package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gamecodeschool.schoolutility.HomeworkAssignment;
import com.gamecodeschool.schoolutility.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by wdwoo on 3/15/2017.
 */

public class DialogAssignHomework extends DialogFragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mHomeworkDatabaseReference;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.homework_assign_description, null);

        //Initializes database and reference to assignments branch
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mHomeworkDatabaseReference = mFirebaseDatabase.getReference().child("assignments");

        //References to all the objects in the dialog
        final EditText assignName = (EditText) dialogView.findViewById(R.id.assign_homework_title);
        final EditText assignDescript = (EditText) dialogView.findViewById(R.id.assign_homework_description);
        final EditText assignDate = (EditText) dialogView.findViewById(R.id.assign_due_date);

        //Progress/Regress buttons
        Button btnCancel = (Button) dialogView.findViewById(R.id.assign_description_cancel);
        Button btnContinue = (Button) dialogView.findViewById(R.id.assign_description_continue);

        builder.setView(dialogView).setMessage("New Assignment:");


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
                        //TODO: This system needs to change so that different homework assignments will be stored in different children of the "assignments" branch
                        HomeworkAssignment homework = new HomeworkAssignment(assignName.getText().toString(), assignDescript.getText().toString(), assignDate.getText().toString());
                        mHomeworkDatabaseReference.push().setValue(homework);

                        dismiss();
                    }
                }
        );

        return builder.create();
    }
}
