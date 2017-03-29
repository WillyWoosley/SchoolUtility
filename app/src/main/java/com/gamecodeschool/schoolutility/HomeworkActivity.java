package com.gamecodeschool.schoolutility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeworkActivity extends AppCompatActivity
    implements MenubarFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
    }

    @Override
    public void onFragmentInteraction(int position)
    {
        //placeholder interaction listener which must be implemented
        //and can be filled with specific actions to be taken when clicked
    }
}
