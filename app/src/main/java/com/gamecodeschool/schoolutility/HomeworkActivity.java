package com.gamecodeschool.schoolutility;

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
import com.google.firebase.database.ValueEventListener;
import com.pacificcollegiate.dialogs.DialogAssignClass;
import com.pacificcollegiate.dialogs.DialogShowHomework;

import java.util.ArrayList;
import java.util.List;

public class HomeworkActivity extends AppCompatActivity
    implements MenubarFragment.OnFragmentInteractionListener {

    //Member Variables//
    private HomeworkAdapter mHomeworkAdapter;
    private ArrayList<String> classesLed = new ArrayList<String>();
    private ArrayList<String> homeworkAssigned = new ArrayList<String>();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mHomeworkAssignedReference;
    private DatabaseReference mAssignmentDatabaseReference;
    private DatabaseReference userClassRef;
    private ChildEventListener mZssignmentChildEventListner;
    private ChildEventListener mHomeworkAssignedChildEventListener;
    private ChildEventListener mClassLedEventListner;
    ////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Clean this up, pending its continuing working
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        //Firebase references
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        //Firebase Database References
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userClassRef = mFirebaseDatabase.getReference().child("users").child(currentUser).child("classesTaught");
        mHomeworkAssignedReference = mFirebaseDatabase.getReference();
        mAssignmentDatabaseReference = mFirebaseDatabase.getReference().child("assignments");


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

        mHomeworkAssignedReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        mHomeworkAdapter.clear();
                        mHomeworkAdapter.notifyDataSetChanged();

                        for (DataSnapshot specifcSnapshot: dataSnapshot.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("homeworkAssigned").getChildren()) {
                            homeworkAssigned.add(specifcSnapshot.getKey());
                        }

                        for (DataSnapshot specificSnapshot: dataSnapshot.child("assignments").getChildren()) {
                            if (homeworkAssigned.contains(specificSnapshot.getKey())) {
                                HomeworkAssignment homeworkAssignment = specificSnapshot.getValue(HomeworkAssignment.class);
                                mHomeworkAdapter.add(homeworkAssignment);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                }
        );

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
        if (mZssignmentChildEventListner != null) {
            mAssignmentDatabaseReference.removeEventListener(mZssignmentChildEventListner);
            mZssignmentChildEventListner = null;
        }

        if (mClassLedEventListner != null)  {
            userClassRef.removeEventListener(mClassLedEventListner);
            mClassLedEventListner = null;
        }

        if (mHomeworkAssignedChildEventListener != null) {
            userClassRef.removeEventListener(mHomeworkAssignedChildEventListener);
            mHomeworkAssignedChildEventListener = null;
        }

    }
}
