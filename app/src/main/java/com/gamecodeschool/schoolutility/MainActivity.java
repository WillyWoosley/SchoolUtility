package com.gamecodeschool.schoolutility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements MenubarFragment.OnFragmentInteractionListener {


    //General to-do's
    //TODO: URGENT: Make placeholder layouts for all the pages which will eventually be implemented
    //TODO: URGENT: due-date textview and actual due-date often are placed over each other, something needs to be modified in the layout file
    //TODO: URGENT: Go through the layouts and make some of the weird off grey text black
    //TODO: Get contact working, with placeholder contacts and ideally a working searchbar, assuming that google already has a pretty well made searchbar provided
    //TODO: Make the article assign dialog appear, and actually do something more than appear (save the information somewhere)
    //TODO: Make all data persistent locally for now
    //TODO: Make events work. Not sure how this is going to look/work, not pressing for the demo
    //TODO: Make the contacts activity layout
    //TODO: Make the events activity layout
    //TODO: Make the news page layout
    //TODO: Make a settings page
    //TODO: Fill in the main activity layout
    //TODO: Make the homework page layout
    //TODO: Make a placeholder image to be contained in the listview if there is no homework the user currently has assigned/to do
    //TODO: Add ability to check off homework assignments from the list to set them as complete
    //TODO: Make the homework listview items images/background vary based upon assignment type/class
    //TODO: Fill in menubar placeholder assets with real images



    //Member variables//
    public static final String ANONYMOUS = "anonymous";

    public static final int RC_SIGN_IN = 1;

    private HomeworkAdapter mHomeworkAdapter;
    private String mUsername;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mAssignmentDatabaseReference;
    ////////////////////

    public void onFragmentInteraction(int position) {
        //Empty method which can be filled with specific actions for the menu bar to take
        //Must be present for the menubar to work
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = ANONYMOUS;

        //Firebase references
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        //Firebase Database References
        mAssignmentDatabaseReference = mFirebaseDatabase.getReference().child("assignments");

        //Sets up the HomeworkAssignment list, adapter, and ListView
        List<HomeworkAssignment> homeworkAssignments = new ArrayList<>();
        mHomeworkAdapter = new HomeworkAdapter(this, R.layout.homework_listview, homeworkAssignments);
        ListView listAssignment = (ListView) findViewById(R.id.homework_listview_display);
        listAssignment.setAdapter(mHomeworkAdapter);

       mAssignmentDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //TODO: Make this add all the other content that will be displayed on the main page
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
        });


        /*
        listAssignment.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Creates reference to clicked assignment
                        HomeworkAssignment tempAssignment = mHomeworkAdapter.getItem(position);
                        //Then creates show dialog, passes assignment reference, and inflates
                        DialogShowHomework showHomework = new DialogShowHomework();
                        showHomework.sendHomework(tempAssignment);
                        showHomework.show(getFragmentManager(), "");
                    }
                }
        );*/

        //Sets up AuthorizationListener
        /*
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //Sets up the ChildEventListner so long as user != null
                if (user != null) {
                    onSignedInInitialize(user.getDisplayName());
                    Toast.makeText(MainActivity.this, "Welcome to Puma Planner!", Toast.LENGTH_SHORT).show();
                } else {
                    //Initializes the FirebaseUI, allowing for user to create a new account
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(), RC_SIGN_IN);
                }
            }
        };*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the admin menu
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //Get reference to pressed option
        int id = item.getItemId();

        if (id==R.id.settingBar)
        {
            //insert code to get to a settings page which will eventually be added
            return true;
        }

        if (id==R.id.addAssignmentDropdown)
        {
            //Creates a new DialogAssignHomework and displays it
            DialogAssignHomework newAssignment = new DialogAssignHomework();
            newAssignment.show(getFragmentManager(), "");
            return true;
        }

        if (id==R.id.addArticleDropdown)
        {
            DialogAddArticle newArticle = new DialogAddArticle();
            newArticle.show(getFragmentManager(), null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
