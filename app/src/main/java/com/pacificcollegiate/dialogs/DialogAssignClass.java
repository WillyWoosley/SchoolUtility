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
    private String[] mClassesLed;
    String mClassName;

    static DialogAssignClass newInstance(String[] classesLed) {
        DialogAssignClass assignClass = new DialogAssignClass();

        Bundle args = new Bundle();
        args.putStringArray("classesLed", classesLed);
        assignClass.setArguments(args);

        return assignClass;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassesLed = getArguments().getStringArray("classesLed");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.homework_assign_class, null);
        builder.setView(dialogView).setMessage("New Assignment:");

        final CheckBox first = (CheckBox) dialogView.findViewById(R.id.classAssignFirst);
        final CheckBox second = (CheckBox) dialogView.findViewById(R.id.classAssignSecond);
        final CheckBox third = (CheckBox) dialogView.findViewById(R.id.classAssignThird);
        final CheckBox fourth = (CheckBox) dialogView.findViewById(R.id.classAssignFourth);
        final CheckBox fifth = (CheckBox) dialogView.findViewById(R.id.classAssignFifth);
        final CheckBox sixth = (CheckBox) dialogView.findViewById(R.id.classAssignSixth);
        CheckBox[] temp = {first, second, third, fourth, fifth, sixth};
        //TODO: Switch these to radio buttons, its janky, but it really can only be for one class
        final List<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        for(CheckBox c : temp) {
            checkBoxes.add(c);
        }

        FirebaseDatabase mRef = FirebaseDatabase.getInstance();

        Button btnOk = (Button) dialogView.findViewById(R.id.assignClassOk);
        btnOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //This does communicate information to the other dialog, it just doesn't work because there are still issues with classLed retrieval
                        for (CheckBox c: checkBoxes) {
                            if (c.isChecked()) {
                                mClassName = c.getText().toString();
                                break;
                            }
                        }

                        DialogFragment assignFragment = DialogAssignHomework.newInstance(mClassName);
                        assignFragment.show(getFragmentManager(),"");
                        dismiss();
                    }
                }
        );

        /*userClassRef.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //TODO: Move this to activities where dialog is created, pass classes as bundle of args
                        SchoolClass schoolClass = dataSnapshot.getValue(SchoolClass.class);
                        classes.add(schoolClass);
                        //checkBoxes.get(0).setText(schoolClass.getClassName());
                        //TODO: This doesn't work because it merely removes the reference from the array, but does not remove it from the view
                        //checkBoxes.remove(0);
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
        );*/

        return builder.create();
    }

}
