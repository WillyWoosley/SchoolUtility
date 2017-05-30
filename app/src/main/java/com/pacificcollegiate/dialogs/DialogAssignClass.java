package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.gamecodeschool.schoolutility.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdwoo on 5/10/2017.
 */

public class DialogAssignClass extends DialogFragment {

    //Member Variables//
    private ArrayList<String> mClassesLed;
    private String mClassName;
    ////////////////////

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Necesary Dialog setup
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.homework_assign_class, null);
        builder.setView(dialogView);
        mClassesLed = getArguments().getStringArrayList("classesLed");

        Button btnOk = (Button) dialogView.findViewById(R.id.assignClassOk);
        Button btnCancel = (Button) dialogView.findViewById(R.id.assignClassCancel);

        //Generates references to all the checkboxes, puts them in a temp array, and transcribes them to a List
        final CheckBox first = (CheckBox) dialogView.findViewById(R.id.classAssignFirst);
        final CheckBox second = (CheckBox) dialogView.findViewById(R.id.classAssignSecond);
        final CheckBox third = (CheckBox) dialogView.findViewById(R.id.classAssignThird);
        final CheckBox fourth = (CheckBox) dialogView.findViewById(R.id.classAssignFourth);
        final CheckBox fifth = (CheckBox) dialogView.findViewById(R.id.classAssignFifth);
        final CheckBox sixth = (CheckBox) dialogView.findViewById(R.id.classAssignSixth);
        CheckBox[] temp = {first, second, third, fourth, fifth, sixth};
        //TODO: Switch these to radio buttons, its janky, but it really can only be for one class
        //TODO: This is janky, make it better if possible
        final List<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        for(CheckBox c : temp) {
            checkBoxes.add(c);
        }

        //Sets text to classes led by the user and vanishes unneeded checkboxes
        for (int i=0; i<6; i++) {
            if (i<mClassesLed.size()) {
                checkBoxes.get(i).setText(mClassesLed.get(i));
            } else {
                checkBoxes.get(i).setVisibility(View.GONE);
            }
        }

        //Handles the next step or cancel
        btnOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (CheckBox c: checkBoxes) {
                            if (c.isChecked()) {
                                mClassName = c.getText().toString();
                                break;
                            }
                        }

                        DialogFragment assignFragment = DialogAssignHomework.newInstance(mClassName);
                        assignFragment.show(getFragmentManager(),"");
                        dismiss();
                    }
                }
        );

        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

        return builder.create();
    }

}
