package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.gamecodeschool.schoolutility.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by wdwoo on 5/7/2017.
 */

public class DialogCreateClass extends DialogFragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mClassesDatabaseReference;
    private DatabaseReference mFirstPeriodDatabaseReference;
    private DatabaseReference mSecondPeriodDatabaseReference;
    private DatabaseReference mThirdPeriodDatabaseReference;
    private DatabaseReference mFourthPeriodDatabaseReference;
    private DatabaseReference mFifthPeriodDatabaseReference;
    private DatabaseReference mSixthPeriodDatabaseReference;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.create_class_dialog, null);
        builder.setView(dialogView).setMessage("New class:");

        final EditText className = (EditText) dialogView.findViewById(R.id.className);
        Button confirm = (Button) dialogView.findViewById(R.id.placeholder);

        //Initialize database and references to all periods
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mClassesDatabaseReference = mFirebaseDatabase.getReference().child("classes");

        //TODO: There may be a cleaner way to do this, look into making it so all the classes don't have to have these unique key identifiers
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mUserDatabaseReference = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid()).child("classesTaught").push();

        mFirstPeriodDatabaseReference = mClassesDatabaseReference.child("first");
        mSecondPeriodDatabaseReference = mClassesDatabaseReference.child("second");
        mThirdPeriodDatabaseReference = mClassesDatabaseReference.child("third");
        mFourthPeriodDatabaseReference = mClassesDatabaseReference.child("fourth");
        mFifthPeriodDatabaseReference = mClassesDatabaseReference.child("fifth");
        mSixthPeriodDatabaseReference = mClassesDatabaseReference.child("sixth");
        final DatabaseReference[] periodDatabaseReferences = {mFirstPeriodDatabaseReference, mSecondPeriodDatabaseReference,
            mThirdPeriodDatabaseReference, mFourthPeriodDatabaseReference, mFifthPeriodDatabaseReference, mSixthPeriodDatabaseReference};

        CheckBox first = (CheckBox) dialogView.findViewById(R.id.firstPeriod);
        CheckBox second = (CheckBox) dialogView.findViewById(R.id.secondPeriod);
        CheckBox third = (CheckBox) dialogView.findViewById(R.id.thirdPeriod);
        CheckBox fourth = (CheckBox) dialogView.findViewById(R.id.fourthPeriod);
        CheckBox fifth = (CheckBox) dialogView.findViewById(R.id.fifthPeriod);
        CheckBox sixth = (CheckBox) dialogView.findViewById(R.id.sixthPeriod);
        final CheckBox[] periods = {first, second, third, fourth, fifth, sixth};

        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i<periods.length; i++) {
                            if (periods[i].isChecked()) {
                                String classname = className.getText().toString();
                                periodDatabaseReferences[i].child(classname).setValue(true);
                                mUserDatabaseReference.child(classname).setValue(true);
                            }
                        }

                        DialogAnotherClass anotherClass = new DialogAnotherClass();
                        anotherClass.show(getFragmentManager(), "");
                        dismiss();
                    }
                }
        );

        return builder.create();
    }
}
