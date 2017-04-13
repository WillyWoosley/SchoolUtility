package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gamecodeschool.schoolutility.HomeworkAssignment;
import com.gamecodeschool.schoolutility.R;

import org.w3c.dom.Text;

/**
 * Created by wdwoo on 3/16/2017.
 */

public class DialogShowHomework extends DialogFragment {

    private HomeworkAssignment mHomework;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.homework_show_assignment, null);

        TextView assignmentName = (TextView) dialogView.findViewById(R.id.show_homework_name);
        TextView assignmentDesc = (TextView) dialogView.findViewById(R.id.show_homework_description);
        TextView assignmentDue = (TextView) dialogView.findViewById(R.id.show_due_date);
        Button btnOk  = (Button) dialogView.findViewById(R.id.ok_button);

        assignmentName.setText(mHomework.getAssignmentName());
        assignmentDesc.setText(mHomework.getAssignmentDescription());
        assignmentDue.setText(mHomework.getDueDate());

        builder.setView(dialogView).setMessage("Homework Assignment:");

        btnOk.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dismiss();
                    }
                }
        );

        return builder.create();
    }

    //Used to pass a Homework, so that the above can be executed
    public void sendHomework(HomeworkAssignment assignment)
    {
        mHomework = assignment;
    }
}
