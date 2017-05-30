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
 * Created by wdwoo on 4/7/2017.
 */

public class HomeworkAdapter extends ArrayAdapter<HomeworkAssignment> {

    public HomeworkAdapter(Context context, int resource, List<HomeworkAssignment> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int whichItem, View view, ViewGroup viewGroup) {
        //Inflates view if it hasn't been
        if (view==null) {
            view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.homework_listview, viewGroup, false);
        }

        //Create a reference to the assignment to be referenced
        HomeworkAssignment tempAssignment = getItem(whichItem);

        //Create references to all pertinent areas of layout
        TextView assignmentName = (TextView) view.findViewById(R.id.assignment_name_display);
        TextView dueDate = (TextView) view.findViewById(R.id.due_date_display);

        //Set the date, name, and image
        assignmentName.setText(tempAssignment.getAssignmentName());
        dueDate.setText(tempAssignment.getDueDate());
        //TODO: Make it so that the imageview varies based upon class type or assignment type

        return view;
    }
}
