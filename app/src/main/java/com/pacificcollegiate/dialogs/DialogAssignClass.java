package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gamecodeschool.schoolutility.R;
import com.gamecodeschool.schoolutility.SchoolClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdwoo on 5/10/2017.
 */

public class DialogAssignClass extends DialogFragment {

    private DatabaseReference mUsersClassesReference;
    private List<SchoolClass> classes = new ArrayList<SchoolClass>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.homework_assign_class, null);
        builder.setView(dialogView).setMessage("New Assignment:");

        Button btnOk = (Button) dialogView.findViewById(R.id.assignClassOk);
        btnOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                }
        );

        final CheckBox first = (CheckBox) dialogView.findViewById(R.id.classAssignFirst);
        final CheckBox second = (CheckBox) dialogView.findViewById(R.id.classAssignSecond);
        final CheckBox third = (CheckBox) dialogView.findViewById(R.id.classAssignThird);
        final CheckBox fourth = (CheckBox) dialogView.findViewById(R.id.classAssignFourth);
        final CheckBox fifth = (CheckBox) dialogView.findViewById(R.id.classAssignFifth);
        final CheckBox sixth = (CheckBox) dialogView.findViewById(R.id.classAssignSixth);
        CheckBox[] temp = {first, second, third, fourth, fifth, sixth};
        final List<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        for(CheckBox c : temp) {
            checkBoxes.add(c);
        }

        FirebaseDatabase mRef = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userClassRef = mRef.getReference().child("users").child(currentUser.getUid()).child("classesTaught");

        userClassRef.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        SchoolClass schoolClass = dataSnapshot.getValue(SchoolClass.class);
                        checkBoxes.get(0).setText(schoolClass.getClassName());
                        checkBoxes.remove(0);
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {}
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                }
        );

        return builder.create();
    }

}
