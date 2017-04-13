package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by wdwoo on 4/10/2017.
 */

public class DialogAreLeader extends DialogFragment {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you head a club at PCS?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Lead in to a dialog that will ask the teacher to somehow validate, and potentially lead in to asking which classes they teach
                        //Placeholder
                        usersDatabaseReference.child("isLeader").setValue(true);
                        dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usersDatabaseReference.child("isLeader").setValue(false);
                        dismiss();
                    }
                });

        return builder.create();
    }
}
