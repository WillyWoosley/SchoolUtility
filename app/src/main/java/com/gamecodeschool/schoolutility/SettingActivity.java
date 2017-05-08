package com.gamecodeschool.schoolutility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pacificcollegiate.dialogs.DialogSetClasses;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //TODO: Make sure that the class select portion of this activity is prepopulated with the classes a user is currently enrolled in

        TextView selectClasses = (TextView) findViewById(R.id.selectClasses);

        selectClasses.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogSetClasses setClasses = new DialogSetClasses();
                        setClasses.show(getFragmentManager(), "");
                    }
                }
        );
    }
}
