package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.gamecodeschool.schoolutility.R;

/**
 * Created by wdwoo on 4/12/2017.
 */

public class DialogSelectClasses extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.student_class_enrolled, null);
        builder.setView(dialogView);

        Spinner firstSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodOne);
        Spinner secondSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodTwo);
        Spinner thirdSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodThree);
        Spinner fourthSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodFour);
        Spinner fifthSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodFive);
        Spinner sixthSpinner = (Spinner) dialogView.findViewById(R.id.spinnerPeriodSix);


        return builder.create();
    }
}
