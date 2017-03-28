package com.gamecodeschool.schoolutility;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by wdwoo on 3/25/2017.
 */

public class MenubarFragment extends Fragment {
    OnFragmentInteractionListener mListener;


    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.menubar_fragment, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //References to bottom action bar options
        ImageButton newsSwap = (ImageButton) getView().findViewById(R.id.mainNews);
        ImageButton contactsSwap = (ImageButton) getView().findViewById(R.id.mainContacts);
        ImageButton homeworkSwap = (ImageButton) getView().findViewById(R.id.mainHomework);
        ImageButton eventsSwap = (ImageButton) getView().findViewById(R.id.mainEvents);

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
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try {
            mListener = (OnFragmentInteractionListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnImageSelectedListener");
        }
    }
}
