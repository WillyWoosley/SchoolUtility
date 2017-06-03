package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gamecodeschool.schoolutility.HomeworkAssignment;
import com.gamecodeschool.schoolutility.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by wdwoo on 3/15/2017.
 */

public class DialogAssignHomework extends DialogFragment {

    //Member Variables//
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mHomeworkDatabaseReference;
    private DatabaseReference mUsersReference;
    private ValueEventListener testListener;
    private String mCurrentUser;
    private String mClassName;
    ////////////////////

    static DialogAssignHomework newInstance(String className) {
        //TODO: Make this so that it will reject the attempted creation if one of the description fields is left blank
        DialogAssignHomework dialogAssignHomework = new DialogAssignHomework();

        Bundle args = new Bundle();
        args.putString("className", className);
        dialogAssignHomework.setArguments(args);

        return dialogAssignHomework;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getArguments().getString("className");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.homework_assign_description, null);
        builder.setView(dialogView).setMessage("New Assignment:");

        //Initializes database and reference to assignments branch
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUsersReference = mFirebaseDatabase.getReference().child("users");
        mHomeworkDatabaseReference = mFirebaseDatabase.getReference().child("assignments");

        //References to all the objects in the dialog
        final EditText assignName = (EditText) dialogView.findViewById(R.id.assign_homework_title);
        final EditText assignDescript = (EditText) dialogView.findViewById(R.id.assign_homework_description);
        final EditText assignDate = (EditText) dialogView.findViewById(R.id.assign_due_date);

        //Progress/Regress buttons
        Button btnCancel = (Button) dialogView.findViewById(R.id.assign_description_cancel);
        Button btnContinue = (Button) dialogView.findViewById(R.id.assign_description_continue);

        btnCancel.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dismiss();
                    }
                }
        );

        btnContinue.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        //Creates HomeworkAssignment with the passed parameters and then sends it to the database
                        //TODO: This system needs to change so that different homework assignments will be stored in different children of the "assignments" branch\
                        String assignmentUid = mHomeworkDatabaseReference.push().getKey();
                        HomeworkAssignment homework = new HomeworkAssignment(assignName.getText().toString(), assignDescript.getText().toString(), assignDate.getText().toString(), mClassName, assignmentUid);
                        mHomeworkDatabaseReference.child(assignmentUid).setValue(homework);
                        assignWorkToStudents(assignmentUid);
                        dismiss();
                    }
                }
        );

        return builder.create();
    }

    public void assignWorkToStudents(final String hwUid) {
                testListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //TODO: Can make this more efficient if it first checks for if the user is a teacher or not
                        //TODO: Can also make this add the homework that has been assigned to a teachers view as assigned homework
                        //TODO: This is always running, but that maynbe fine
                        for (DataSnapshot specificSnapshot: dataSnapshot.getChildren()) {
                            String user = specificSnapshot.getKey();
                            if (mCurrentUser.equals(user)) {
                                mUsersReference.child(user).child("homeworkAssigned").child(hwUid).setValue(true);
                            }
                            DataSnapshot usersClasses = specificSnapshot.child("classesEnrolled");
                            for (DataSnapshot test : usersClasses.getChildren()) {
                                if (test.getValue().equals(mClassName)) {
                                    mUsersReference.child(user).child("homeworkAssigned").child(hwUid).setValue(true);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };

                mUsersReference.addValueEventListener(testListener);
    }

    public void removeListeners() {
        mUsersReference.removeEventListener(testListener);
    }
}
