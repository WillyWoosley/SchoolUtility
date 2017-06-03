package com.pacificcollegiate.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * Created by wdwoo on 5/29/2017.
 */

public class DialogCreateEventDay extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Bundle toPass = new Bundle();
        toPass.putInt("year", year);
        toPass.putInt("month", month + 1);
        toPass.putInt("day", day);
        DialogCreateEventTime createEventTime = new DialogCreateEventTime();
        createEventTime.setArguments(toPass);
        createEventTime.show(getFragmentManager(), "");
    }
}