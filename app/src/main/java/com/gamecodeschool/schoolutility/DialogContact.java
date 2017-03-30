package com.gamecodeschool.schoolutility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by wdwoo on 3/30/2017.
 */

public class DialogContact extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.contact_show, null);

        Button okButton = (Button) dialogView.findViewById(R.id.okContactDialog);

        builder.setView(dialogView).setMessage("My Contact");

        okButton.setOnClickListener(
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
}
