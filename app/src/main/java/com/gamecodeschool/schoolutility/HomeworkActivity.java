package com.gamecodeschool.schoolutility;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mAssignmentDatabaseReference;
    private ChildEventListener mAssignmentChildEventListner;
    ////////////////////

    public void onMenubarFragmentInteraction(int position) {
        //Must be present for Menubar to work
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        //Firebase references
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        //Firebase Database References
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
    }

    public void detachDatabaseReadListners() {
        //TODO: Make this detach other ChildEventListner's if they get added, or disregard if they do not
        if (mAssignmentChildEventListner != null) {
            mAssignmentDatabaseReference.removeEventListener(mAssignmentChildEventListner);
            mAssignmentChildEventListner = null;
        }
    }
}
