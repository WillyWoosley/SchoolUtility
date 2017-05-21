package com.gamecodeschool.schoolutility;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pacificcollegiate.dialogs.DialogAssignClass;
import com.pacificcollegiate.dialogs.DialogAssignHomework;
import com.pacificcollegiate.dialogs.DialogShowHomework;

import java.util.ArrayList;
import java.util.List;

public class HomeworkActivity extends AppCompatActivity
    implements MenubarFragment.OnFragmentInteractionListener {

    //Member Variables//
    private HomeworkAdapter mHomeworkAdapter;
    private ArrayList<String> classesLed = new ArrayList<String>();
    static private int mStackLevel = 0;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mAssignmentDatabaseReference;
    private DatabaseReference userClassRef;
    private ChildEventListener mAssignmentChildEventListner;
    private ChildEventListener mClassLedEventListner;
    ////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        //Firebase references
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        //Firebase Database References
        mAssignmentDatabaseReference = mFirebaseDatabase.getReference().child("assignments");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userClassRef = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid()).child("classesTaught");

        //Sets up the HomeworkAssignment list, adapter, and ListView
        List<HomeworkAssignment> homeworkAssignments = new ArrayList<>();
        mHomeworkAdapter = new HomeworkAdapter(this, R.layout.homework_listview, homeworkAssignments);
        ListView listAssignment = (ListView) findViewById(R.id.homework_listview_display);
        listAssignment.setAdapter(mHomeworkAdapter);

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

                        for (String s : classesLed) {
                            Toast.makeText(HomeworkActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        RelativeLayout addAssignmentLayout = (RelativeLayout) findViewById(R.id.homeworkAddAssignmentLayout);

        addAssignmentLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogAssignClass assignClass = new DialogAssignClass();
                        Bundle toPass = new Bundle();
                        toPass.putStringArrayList("classesLed", classesLed);
                        assignClass.setArguments(toPass);
                        assignClass.show(getFragmentManager(), "");
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        attachDatabaseReadListner();
    }

    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Checks which button has been pressed from the options menu and acts accordingly
        switch (item.getItemId()) {
            case R.id.settingBar:
                Intent settingsIntent = new Intent(HomeworkActivity.this, SettingActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.signOut:
                //Signs user out
                AuthUI.getInstance().signOut(this);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onMenubarFragmentInteraction(int position) {
        //Must be present for Menubar to work
    }

    public void attachDatabaseReadListner() {
        if (mAssignmentChildEventListner == null) {
            mAssignmentChildEventListner = new  ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
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

        if (mClassLedEventListner == null) {
            mClassLedEventListner = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    SchoolClass schoolClass = dataSnapshot.getValue(SchoolClass.class);
                    classesLed.add(schoolClass.getClassName());
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
            userClassRef.addChildEventListener(mClassLedEventListner);
        }
    }

    public void detachDatabaseReadListners() {
        //TODO: Make this detach other ChildEventListner's if they get added, or disregard if they do not
        if (mAssignmentChildEventListner != null) {
            mAssignmentDatabaseReference.removeEventListener(mAssignmentChildEventListner);
            mAssignmentChildEventListner = null;
        }

        if (mClassLedEventListner != null)  {
            userClassRef.removeEventListener(mClassLedEventListner);
            mClassLedEventListner = null;
        }
    }
}
