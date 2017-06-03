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
 * Created by wdwoo on 5/30/2017.
 */

public class DialogValidateTeacher extends DialogFragment {

    //Member Variables//
    String VALIDATION_CODE = "puma";
    ////////////////////

    @Override
    public Dialog onCreateDialog(Bundle saveddInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.teacher_confirm, null);
        builder.setView(dialogView);

        Button okBtn = (Button) dialogView.findViewById(R.id.btnConfirm);
        Button cancelButton = (Button) dialogView.findViewById(R.id.btnCancel);
        final EditText validationCode = (EditText) dialogView.findViewById(R.id.validationCode);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());

        okBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String enteredCode = validationCode.getText().toString();
                        if (enteredCode.equals(VALIDATION_CODE)) {
                            usersDatabaseReference.child("isTeacher").setValue(true);
                            DialogCreateClass createClass = new DialogCreateClass();
                            createClass.show(getFragmentManager(), "");
                            dismiss();
                        }
                    }
                }
        );

        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        usersDatabaseReference.child("isTeacher").setValue(false);
                        dismiss();
                    }
                }
        );
        return builder.create();
    }

}
