package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gamecodeschool.schoolutility.Events;
import com.gamecodeschool.schoolutility.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by wdwoo on 5/29/2017.
 */

public class DialogCreateEvent extends DialogFragment{

    //Member Variables//
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventReference;
    int inputYear;
    int inputMonth;
    int inputDayOfMonth;
    int inputHour;
    int inputMinute;
    ////////////////////

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputYear = getArguments().getInt("year");
        inputMonth = getArguments().getInt("month");
        inputDayOfMonth = getArguments().getInt("day");
        inputHour = getArguments().getInt("hour");
        inputMinute = getArguments().getInt("minute");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.event_create, null);
        builder.setView(dialogView);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventReference = mFirebaseDatabase.getReference().child("events");

        Button btnOk = (Button) dialogView.findViewById(R.id.buttonOk);
        Button btnCancel = (Button) dialogView.findViewById(R.id.buttonCancel);
        final EditText eventTitle = (EditText) dialogView.findViewById(R.id.eventTitle);
        final EditText eventDesc = (EditText) dialogView.findViewById(R.id.eventDescription);

        btnOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Events events = new Events(inputYear, inputMonth, inputDayOfMonth, inputHour, inputMinute,
                                eventDesc.getText().toString(), eventTitle.getText().toString());
                        mEventReference.push().setValue(events);
                        dismiss();
                    }
                }
        );

        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );
        return builder.create();
    }

}
