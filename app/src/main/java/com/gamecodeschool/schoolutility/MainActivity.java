package com.gamecodeschool.schoolutility;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pacificcollegiate.dialogs.DialogAreLeader;
import com.pacificcollegiate.dialogs.DialogAreTeacher;
import com.pacificcollegiate.dialogs.DialogAssignClass;
import com.pacificcollegiate.dialogs.DialogAssignHomework;
import com.pacificcollegiate.dialogs.DialogCreateClass;
import com.pacificcollegiate.dialogs.DialogCreateClub;
import com.pacificcollegiate.dialogs.DialogShowHomework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements MenubarFragment.OnFragmentInteractionListener {


    //General to-do's
    //TODO: Get contact working, with placeholder contacts and ideally a working searchbar, assuming that google already has a pretty well made searchbar provided
    //TODO: Make events page layout
    //TODO: Make events work
    //TODO: Make a settings page
    //TODO: Fill in the main activity layout
    //TODO: Make a placeholder image to be contained in the listview if there is no homework the user currently has assigned/to do
    //TODO: Add ability to check off homework assignments from the list to set them as complete
    //TODO: Make the homework listview items images/background vary based upon assignment type/class
    //TODO: Fill in menubar placeholder assets with real images
    //TODO: Make some sort of display that notifies a user of new things that is worth their seeing ("worth seeing" can be set in settings). This will need to use a Transaction
    //TODO: You can only log out from the MainActivity page
    //TODO: Make it so this behaves like HomeworkActivity in terms of assignment inflation



    //Member variables//
    public static final String ANONYMOUS = "anonymous";

    public static final int RC_SIGN_IN = 1;

    private HomeworkAdapter mHomeworkAdapter;
    private String mUsername;
    private EventAdapter mEventAdapter;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mAssignmentDatabaseReference;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mEventsDatabaseReference;
    private ChildEventListener mAssignmentChildEventListner;
    private ChildEventListener mEventChildEventListner;
    ////////////////////

    public void onMenubarFragmentInteraction(int position) {
        //Empty method which can be filled with specific actions for the menu bar to take
        //Must be present for the menubar to work
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = ANONYMOUS;
        //May or may not need, alternative for user authentication with permissions
        //mSharedPreferences = getSharedPreferences("MAIN_PREFS", 0);

        //Firebase references
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();



        //Firebase Database References
        mAssignmentDatabaseReference = mFirebaseDatabase.getReference().child("assignments");
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("users");
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");

        //Sets up the HomeworkAssignment list, adapter, and ListView
        List<HomeworkAssignment> homeworkAssignments = new ArrayList<>();
        mHomeworkAdapter = new HomeworkAdapter(this, R.layout.homework_listview, homeworkAssignments);
        ListView listAssignment = (ListView) findViewById(R.id.homework_listview_display);
        listAssignment.setAdapter(mHomeworkAdapter);

        //Sets up Events list, adapter, and ListView
        List<Events> events = new ArrayList<>();
        mEventAdapter = new EventAdapter(this, R.layout.event_listview, events);
        ListView listEvents = (ListView) findViewById(R.id.mainEventListViewDisplay);
        listEvents.setAdapter(mEventAdapter);

        mAuthStateListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //Sets up ChildEventListner so long as user is logged in
                if (user != null) {
                    //Original
                    //onSignedInInitialize(user.getDisplayName(), userUid);
                    onSignedInInitialize(user);
                } else {
                    //Initiates account creation/login if the user is not logged in
                    //TODO: Specify a custom theme to be used here, once we have a custom theme for the rest of the app
                    //TODO: Re-enable smartlock when its time
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    public void onStart() {
        //Adds AuthStateListener whenever MainActivity is started
        //Maybe onResume/onPause?
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }

    @Override
    public void onStop() {
        //Removes AuthStateListener whenever MainActivity is stopped
        super.onStop();
        if (mAuthStateListner != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListner);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu
        //TODO: Make this so that it inflates different options from this menu for different users
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //Checks which button has been pressed from the options menu and acts accordingly
        switch (item.getItemId()) {
            case R.id.settingBar:
                Intent settingsIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.signOut:
                //Signs user out
                AuthUI.getInstance().signOut(this);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                //Successful sign in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(MainActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
                mUsername = user.getDisplayName();

                //Looks in the user portion of the database, and checks if there is already a user with thte Uid of the user currently using the service
                mUsersDatabaseReference.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.hasChild(user.getUid())) {
                                    //TODO: Get all the necesary fields here, such as AUTH_STATUS, courses led or enrolled in, etc. (possibly do this through series of DialogFragments and AlertDialogs)
                                    DatabaseReference userReference = mUsersDatabaseReference.child(user.getUid());
                                    userReference.child("name").setValue(user.getDisplayName());
                                    userReference.child("email").setValue(user.getEmail());

                                    //Dialogs which will appear and handle user input/validation for leaders and teachers
                                    DialogAreTeacher areTeacher = new DialogAreTeacher();
                                    areTeacher.show(getFragmentManager(), "");

                                    //TODO: Create a storage repository of images, and randomly select one to be set as a users badge profile, except for maybe not teachers
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        }
                );

                //TODO: Make this remove non-teachers views of the addAssignment button

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void onSignedInInitialize(final FirebaseUser user) {
        //Sets username and attaches ChildEventListner
        mUsername = user.getDisplayName();
        TextView greeting = (TextView) findViewById(R.id.mainGreeting);
        greeting.setTypeface(null, Typeface.ITALIC);
        String formattedGreeting = "Welcome " + mUsername + ", to Puma Planner";
        greeting.setText(formattedGreeting);
        attachDatabaseReadListner();
    }

    public void onSignedOutCleanup() {
        //Sets user back to anon, and removes displays and ChildEventListners
        mUsername = ANONYMOUS;
        mHomeworkAdapter.clear();
        detachDatabaseReadListners();
    }

    public void attachDatabaseReadListner() {
        //TODO: Make this attach all ChildEventListner's for other branches, or give ChildEventListener ability to differentiate between objects and act accordingly
        if (mAssignmentChildEventListner == null) {
            mAssignmentChildEventListner = new  ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //TODO: (Maybe) Make this add all the other content that will be displayed on the main page, or create other ChildEventListener's to do this
                    HomeworkAssignment homeworkAssignment = dataSnapshot.getValue(HomeworkAssignment.class);
                    mHomeworkAdapter.add(homeworkAssignment);
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };

            mAssignmentDatabaseReference.addChildEventListener(mAssignmentChildEventListner);
        }

        if (mEventChildEventListner == null) {
            mEventChildEventListner = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Events event = dataSnapshot.getValue(Events.class);
                    mEventAdapter.add(event);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mEventsDatabaseReference.addChildEventListener(mEventChildEventListner);
        }

    }

    public void detachDatabaseReadListners() {
        //TODO: Make this detach other ChildEventListner's if they get added, or disregard if they do not
        if (mAssignmentChildEventListner != null) {
            mAssignmentDatabaseReference.removeEventListener(mAssignmentChildEventListner);
            mAssignmentChildEventListner = null;
        }

        if (mEventChildEventListner != null) {
            mEventsDatabaseReference.removeEventListener(mEventChildEventListner);
            mEventChildEventListner = null;
        }
    }
}
