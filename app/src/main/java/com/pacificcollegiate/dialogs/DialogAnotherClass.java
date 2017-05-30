package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by wdwoo on 5/9/2017.
 */

public class DialogAnotherClass extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("If you have another class that you teach, click 'Add Another'," +
                " otherwise, hit continue")
            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            })
            .setNegativeButton("Add Another", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DialogCreateClass createClass = new DialogCreateClass();
                    createClass.show(getFragmentManager(), "");
                    dismiss();
                }
            });
        return builder.create();
    }
}
