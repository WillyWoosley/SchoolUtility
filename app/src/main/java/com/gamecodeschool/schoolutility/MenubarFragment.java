package com.gamecodeschool.schoolutility;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by wdwoo on 3/25/2017.
 */

public class MenubarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.menubar_fragment, container, false);

        //References to bottom action bar options
        ImageButton newsSwap = (ImageButton) rootView.findViewById(R.id.mainNews);
        ImageButton contactsSwap = (ImageButton) rootView.findViewById(R.id.mainContacts);
        ImageButton homeworkSwap = (ImageButton) rootView.findViewById(R.id.mainHomework);
        ImageButton eventsSwap = (ImageButton) rootView.findViewById(R.id.mainEvents);

        //Creates Intents for all buttons
        final Intent homeworkIntent = new Intent(getActivity(), HomeworkActivity.class);

        homeworkSwap.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        startActivity(homeworkIntent);
                    }
                }
        );

        return rootView;
    }
}
