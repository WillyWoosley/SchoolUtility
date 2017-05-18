package com.gamecodeschool.schoolutility;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdwoo on 4/27/2017.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
    public ContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
    }

    //Implements the ListView
    @Override
    public View getView(int whichItem, View view, ViewGroup viewGroup)
    {
        //Inflates if it hasn't already been done
        if (view == null) {
            view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.contact_listview, viewGroup, false);
        }

        //Create a reference to the assignment in question
        Contact tempContact = getItem(whichItem);

        TextView contactName = (TextView) view.findViewById(R.id.contactName);
        TextView isTeacher = (TextView) view.findViewById(R.id.isTeacher);
        TextView isLeader = (TextView) view.findViewById(R.id.isLeader);
        contactName.setText(tempContact.getName());
        //TODO: Implement something which changes contact badges. This will also probably have to be tied in with account creeation
        //TODO: You are here, it doesn't get the boolean, no getter method
        if (tempContact.isIsTeacher()) {
            isTeacher.setText(R.string.teacher);
        } else {
            isTeacher.setText(R.string.student);
        }

        if (tempContact.isIsLeader()) {
            isLeader.setText(R.string.leader);
        } else {
            isLeader.setVisibility(View.GONE);
        }

        return view;
    }
}
