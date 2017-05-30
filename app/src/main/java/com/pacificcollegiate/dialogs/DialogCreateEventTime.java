package com.pacificcollegiate.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.TimePicker;

/**
 * Created by wdwoo on 5/29/2017.
 */

public class DialogCreateEventTime extends DialogFragment implements TimePickerDialog.OnTimeSetListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Bundle toPass = getArguments();
        toPass.putInt("hour", hourOfDay);
        toPass.putInt("minute", minute);
        DialogCreateEvent createEvent = new DialogCreateEvent();
        createEvent.setArguments(toPass);
        createEvent.show(getFragmentManager(), "");
    }
}
