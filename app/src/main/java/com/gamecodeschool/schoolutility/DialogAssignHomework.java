package com.gamecodeschool.schoolutility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by wdwoo on 3/15/2017.
 */

public class DialogAssignHomework extends DialogFragment {
    //Placeholder class which will be used for teachers to assign homework, writing the information provided to HomeworkAssignment objects

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.homework_assign_description, null);

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
                        HomeworkAssignment homework = new HomeworkAssignment();

                        //Retrieve user input for name, date, description and store it in homework object
                        homework.setAssignmentName(assignName.getText().toString());
                        homework.setAssignmentDescription(assignDescript.getText().toString());
                        homework.setDueDate(assignDate.getText().toString());

                        //Get a reference to MainActivity, and then pass the Homwork assignment to it
                        MainActivity callingActivity = (MainActivity) getActivity();
                        callingActivity.createHomeworkAssignment(homework);

                        dismiss();
                    }
                }
        );

        return builder.create();
    }
}
