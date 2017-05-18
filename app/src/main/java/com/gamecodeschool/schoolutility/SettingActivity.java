package com.gamecodeschool.schoolutility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pacificcollegiate.dialogs.DialogSelectClasses;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mClassesReference;

    String[] periods = {"first", "second", "third", "fourth", "fifth", "sixth"};
    ArrayList<String> firstClasses = new ArrayList<String>();
    ArrayList<String> secondClasses = new ArrayList<String>();
    ArrayList<String> thirdClasses = new ArrayList<String>();
    ArrayList<String> fourthClasses = new ArrayList<String>();
    ArrayList<String> fifthClasses = new ArrayList<String>();
    ArrayList<String> sixthClasses = new ArrayList<String>();
    ArrayList[] allClasses = {firstClasses, secondClasses, thirdClasses, fourthClasses, fifthClasses, sixthClasses};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //TODO: Make sure that the class select portion of this activity is prepopulated with the classes a user is currently enrolled in

        Button selectClasses = (Button) findViewById(R.id.selectClasses);



        selectClasses.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i=0; i<periods.length; i++) {
                            //TODO: You are here, check if arraylist[] is working, then make a for loop which will put all lists in a bundle and pass them to the selectclassdialog
                        }
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        //Replace this with a single reference which refers to "classes" and then a string[] with periods
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mClassesReference = mFirebaseDatabase.getReference().child("classes");
        changeClasses();
    }

    public void changeClasses() {
        for (int i=0; i<periods.length; i++) {
            mClassesReference.child(periods[i]).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int iterator = 0;
                    for (int j = 0; j<allClasses.length; j++) {
                        if (allClasses[j].size() == 0) {
                            iterator = j;
                            break;
                        }
                    }

                    for (DataSnapshot specificSnapshot: dataSnapshot.getChildren()) {
                        //TODO: May be able to make this retrieve less data
                        String className = specificSnapshot.getKey();
                        allClasses[iterator].add(className);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


}
