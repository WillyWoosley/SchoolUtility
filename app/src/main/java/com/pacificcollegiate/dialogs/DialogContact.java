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

import org.w3c.dom.Text;

/**
 * Created by wdwoo on 3/30/2017.
 */

public class DialogContact extends DialogFragment {

    Contact mContact;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.contact_show, null);
        builder.setView(dialogView).setMessage("My Contact");

        Button okButton = (Button) dialogView.findViewById(R.id.okContactDialog);
        TextView contactEmail = (TextView) dialogView.findViewById(R.id.contactEmail);
        TextView contactName = (TextView) dialogView.findViewById(R.id.textView6);

        contactEmail.setText(mContact.getEmail());
        contactName.setText(mContact.getName());


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

    public void sendContact(Contact contact) {
        mContact = contact;
    }
}
