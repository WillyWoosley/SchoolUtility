package com.gamecodeschool.schoolutility;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pacificcollegiate.dialogs.DialogCreateEventDay;
import com.pacificcollegiate.dialogs.DialogShowEvent;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity
    implements MenubarFragment.OnFragmentInteractionListener {

    //Member Variables//
    private EventAdapter mEventAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventDatabaseReference;
    private ChildEventListener mEventChildEventListner;
    ////////////////////

    //Placeholder activity for EventsActivity, here to get menubar working
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventDatabaseReference = mFirebaseDatabase.getReference().child("events");

        Button test = (Button) findViewById(R.id.testButton);

        List<Events> eventses = new ArrayList<>();
        mEventAdapter = new EventAdapter(this, R.layout.event_listview, eventses);
        ListView listEvents =(ListView) findViewById(R.id.eventListViewDisplay);
        listEvents.setAdapter(mEventAdapter);

        listEvents.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Events tempEvent = mEventAdapter.getItem(position);
                        DialogShowEvent showEvent = new DialogShowEvent();
                        showEvent.sendEvent(tempEvent);
                        showEvent.show(getFragmentManager(), "");
                    }
                }
        );

        test.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogCreateEventDay event = new DialogCreateEventDay();
                        event.show(getFragmentManager(), "");
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Checks which button has been pressed from the options menu and acts accordingly
        switch (item.getItemId()) {
            case R.id.settingBar:
                Intent settingsIntent = new Intent(EventsActivity.this, SettingActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.signOut:
                //Signs user out
                AuthUI.getInstance().signOut(this);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        attachDatabaseListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
    }

    @Override
    public void onMenubarFragmentInteraction(int position) {
        //placeholder interaction listener which must be implemented
        //and can be filled with specific actions to be taken when clicked
    }

    public void attachDatabaseListener() {
        if (mEventChildEventListner == null) {
            mEventChildEventListner = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Events tempEvents = dataSnapshot.getValue(Events.class);
                    mEventAdapter.add(tempEvents);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }

        mEventDatabaseReference.addChildEventListener(mEventChildEventListner);
    }

    public void detachDatabaseReadListener() {
        if (mEventChildEventListner != null) {
            mEventDatabaseReference.removeEventListener(mEventChildEventListner);
            mEventChildEventListner = null;
        }
    }
}
