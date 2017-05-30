package com.gamecodeschool.schoolutility;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wdwoo on 5/29/2017.
 */

public class EventAdapter extends ArrayAdapter<Events> {

    public EventAdapter(Context context, int resource, List<Events> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int whichItem, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.event_listview, viewGroup, false);
        }

        Events tempEvents = getItem(whichItem);

        TextView eventName = (TextView) view.findViewById(R.id.eventListViewName);
        TextView eventDay = (TextView) view.findViewById(R.id.eventListViewDay);
        TextView eventTime = (TextView) view.findViewById(R.id.eventListViewTime);

        int formattedHour = tempEvents.getHour();
        if (formattedHour%12 == 0) {
            formattedHour = 12;
        } else {
            formattedHour = formattedHour%12;
        }

        String formattedDay = tempEvents.getMonth() + "/" + tempEvents.getDayOfMonth() + "/" + tempEvents.getYear();
        String formattedTime = "Starting at: " + formattedHour + ":" + tempEvents.getMinute();

        eventName.setText(tempEvents.getEventName());
        eventDay.setText(formattedDay);
        eventTime.setText(formattedTime);

        return view;
    }

}
