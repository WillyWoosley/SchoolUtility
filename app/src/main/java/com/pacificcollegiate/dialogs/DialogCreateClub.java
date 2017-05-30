package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gamecodeschool.schoolutility.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by wdwoo on 5/18/2017.
 */

public class DialogCreateClub extends DialogFragment {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mUserReference;
    DatabaseReference mClubsReference;
    FirebaseUser currentUser;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.create_club_dialog, null);
        builder.setView(dialogView);

        Button btnConfirm = (Button) dialogView.findViewById(R.id.createClassCreate);
        Button btnCancel = (Button) dialogView.findViewById(R.id.createClassDismiss);
        final EditText clubName = (EditText) dialogView.findViewById(R.id.createClubName);
        final EditText clubDesc = (EditText) dialogView.findViewById(R.id.createClubDescription);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserReference = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid()).child("clubsLed");
        mClubsReference = mFirebaseDatabase.getReference().child("clubs");

        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

        btnConfirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUserReference.push().child(clubName.getText().toString()).setValue(clubDesc.getText().toString());
                        mClubsReference.push().child(clubName.getText().toString()).setValue(clubDesc.getText().toString());
                        dismiss();
                    }
                }
        );

        return builder.create();
    }

}
