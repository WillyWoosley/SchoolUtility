package com.pacificcollegiate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gamecodeschool.schoolutility.Events;
import com.gamecodeschool.schoolutility.R;

/**
 * Created by wdwoo on 5/30/2017.
 */

public class DialogShowEvent extends DialogFragment {

    //Member Variables//
    private Events mEvent;
    ////////////////////

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.event_show, null);
        builder.setView(dialogView);

        TextView eventDesc = (TextView) dialogView.findViewById(R.id.eventShowDescription);
        TextView eventName = (TextView) dialogView.findViewById(R.id.eventShowName);
        TextView eventDate = (TextView) dialogView.findViewById(R.id.eventShowDate);
        TextView eventTime = (TextView) dialogView.findViewById(R.id.eventShowTime);
        Button okBtn = (Button) dialogView.findViewById(R.id.okButton);

        String formattedDate = mEvent.getMonth() + "/" + mEvent.getDayOfMonth() + "/" + mEvent.getYear();
        int formattedHour = mEvent.getHour();
        if (formattedHour%12 == 0) {
            formattedHour = 12;
        } else {
            formattedHour = formattedHour%12;
        }

        eventDesc.setText(mEvent.getDescription());
        eventName.setText(mEvent.getEventName());
        eventDate.setText(formattedDate);
        eventTime.setText("At: " + formattedHour + ":" + mEvent.getMinute());


        okBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

        return builder.create();
    }

    public void sendEvent(Events event) {
        mEvent = event;
    }

}
