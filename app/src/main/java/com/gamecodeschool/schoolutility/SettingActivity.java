package com.gamecodeschool.schoolutility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pacificcollegiate.dialogs.DialogSelectClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    //Member Variables//
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mClassesReference;
    DatabaseReference mUsersClassesReference;
    FirebaseUser mCurrentUser;

    String[] periods = {"first", "second", "third", "fourth", "fifth", "sixth"};
    ArrayList<String> firstClasses = new ArrayList<String>();
    ArrayList<String> secondClasses = new ArrayList<String>();
    ArrayList<String> thirdClasses = new ArrayList<String>();
    ArrayList<String> fourthClasses = new ArrayList<String>();
    ArrayList<String> fifthClasses = new ArrayList<String>();
    ArrayList<String> sixthClasses = new ArrayList<String>();
    ArrayList[] allClasses = {firstClasses, secondClasses, thirdClasses, fourthClasses, fifthClasses, sixthClasses};

    Map currentClasses = new HashMap();
    ////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Layout references creation
        TextView selectClasses = (TextView) findViewById(R.id.selectClasses);

        //Creation of firebase references
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mClassesReference = mFirebaseDatabase.getReference().child("classes");
        mUsersClassesReference = mFirebaseDatabase.getReference().child("users").child(mCurrentUser.getUid()).child("classesEnrolled");

        //Handling of clickable components
        selectClasses.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Creates a bundle of all classes in the database to be passed to a DialogSelectClasses, passes, and displays the Dialog
                        DialogSelectClasses dialogSelectClasses = new DialogSelectClasses();
                        Bundle toPass = new Bundle();
                        for (int i=0; i<periods.length; i++) {
                               toPass.putStringArrayList(periods[i], allClasses[i]);
                        }
                        dialogSelectClasses.setArguments(toPass);
                        dialogSelectClasses.show(getFragmentManager(), "");
                    }
                }
        );
    }

    @Override
    public void onStart() {
        //Reads from the database
        super.onStart();
        readClasses();
    }

    public void populateClasses() {
        //TODO: If desired, could replace with .get with .getOrDefault, so if no class enrollment some default value will be used, such as "No Enrollment"
        //Gets references to all current class TextViews, and sets their text to their corresponding values from the currentClasses HashMap
        TextView currentFirstPeriod = (TextView) findViewById(R.id.settingsPeriodOne);
        TextView currentSecondPeriod = (TextView) findViewById(R.id.settingsPeriodTwo);
        TextView currentThirdPeriod = (TextView) findViewById(R.id.settingsPeriodThree);
        TextView currentFourthPeriod = (TextView) findViewById(R.id.settingsPeriodFour);
        TextView currentFifthPeriod = (TextView) findViewById(R.id.settingsPeriodFive);
        TextView currentSixthPeriod = (TextView) findViewById(R.id.settingsPeriodSix);
        currentFirstPeriod.setText((String) currentClasses.get("first"));
        currentSecondPeriod.setText((String) currentClasses.get("second"));
        currentThirdPeriod.setText((String) currentClasses.get("third"));
        currentFourthPeriod.setText((String) currentClasses.get("fourth"));
        currentFifthPeriod.setText((String) currentClasses.get("fifth"));
        currentSixthPeriod.setText((String) currentClasses.get("sixth"));
    }

    public void readClasses() {
        //Attaches ValueEventListeners for all ClassReferences in the database, and puts all their values into ArrayLists in the allClasses ArrayList[]
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
                public void onCancelled(DatabaseError databaseError) {}
            });

        }

        //Gets the users current classes from the Database, puts them in the currentClasses Map, and passes them to populateClasses()
        mUsersClassesReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot specificData: dataSnapshot.getChildren()) {
                            currentClasses.put(specificData.getKey(), specificData.getValue());
                        }
                        populateClasses();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                }
        );
    }

    //TODO: This never detaches the listener, that may or may not be necesary

}
