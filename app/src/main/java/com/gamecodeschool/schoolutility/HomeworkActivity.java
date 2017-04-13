package com.gamecodeschool.schoolutility;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pacificcollegiate.dialogs.DialogAssignHomework;

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

        RelativeLayout addAssignmentLayout = (RelativeLayout) findViewById(R.id.homeworkAddAssignmentLayout);

        addAssignmentLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogAssignHomework newAssignment = new DialogAssignHomework();
                        newAssignment.show(getFragmentManager(), "");
                    }
                }
        );
    }
}
