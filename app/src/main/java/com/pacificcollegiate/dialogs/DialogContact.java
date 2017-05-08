package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gamecodeschool.schoolutility.Contact;
import com.gamecodeschool.schoolutility.R;

/**
 * Created by wdwoo on 3/30/2017.
 */

public class DialogContact extends DialogFragment {

    //Member Variables//
    private Contact mContact;
    ////////////////////

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.contact_show, null);
        builder.setView(dialogView);

        Button okButton = (Button) dialogView.findViewById(R.id.showContactOk);
        TextView contactName = (TextView) dialogView.findViewById(R.id.showContactName);
        TextView contactEmail = (TextView) dialogView.findViewById(R.id.showContactEmail);

        contactName.setText(mContact.getName());
        contactEmail.setText(mContact.getEmail());

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

    //Used to pass Contact
    public void sendContact(Contact contact) {
        mContact = contact;
    }
}
