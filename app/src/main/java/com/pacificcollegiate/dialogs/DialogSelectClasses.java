package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.gamecodeschool.schoolutility.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by wdwoo on 4/12/2017.
 */

public class DialogSelectClasses extends DialogFragment {

    //Member Variables//
    String[] periods = {"first", "second", "third", "fourth", "fifth", "sixth"};
    ArrayList<String>[] allClasses = new ArrayList[6];

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mUserReference;
    ////////////////////

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.student_class_enrolled, null);
        builder.setView(dialogView);


        //TODO: Maybe find a way thats less janky than just appending "" to all the lists in order for the spinners to not automatically be set to the wrong value
        for (int i=0; i<periods.length; i++) {
            allClasses[i] = getArguments().getStringArrayList(periods[i]);
            if (allClasses[i].get(0) != "") {
                allClasses[i].add(0, "");
            }
        }


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserReference = mFirebaseDatabase.getReference().child("users").child(mCurrentUser.getUid()).child("classesEnrolled");

        Button btnOk = (Button) dialogView.findViewById(R.id.classEnrolledOk);

        final Spinner firstSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodOne);
        Spinner secondSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodTwo);
        Spinner thirdSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodThree);
        Spinner fourthSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodFour);
        Spinner fifthSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodFive);
        Spinner sixthSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodSix);
        final Spinner[] spinners = {firstSpinner, secondSpinner, thirdSpinner, fourthSpinner, fifthSpinner, sixthSpinner};

        for (int i=0; i<periods.length; i++) {
            //TODO: Make this not an error
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, allClasses[i]);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinners[i].setAdapter(arrayAdapter);
        }

        btnOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: Get the selected values from the spinners and write them to the users ~/classesEnrolled/%PERIOD%/
                        //May also want to write to a seperate area that has all the classes, might make it easier to find homework assignments
                        for (int i=0; i<spinners.length; i++) {
                            String className = spinners[i].getSelectedItem().toString();
                            if (className != "") {
                                mUserReference.child(periods[i]).setValue(className);
                            }
                        }
                        dismiss();
                    }
                }
        );

        return builder.create();
    }
}
