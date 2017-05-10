package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.gamecodeschool.schoolutility.R;

/**
 * Created by wdwoo on 5/10/2017.
 */

public class DialogAssignClass extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.homework_assign_class, null);
        builder.setView(dialogView).setMessage("New Assignment:");

        //TODO: You are here

        return builder.create();
    }
}
