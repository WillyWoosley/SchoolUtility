package com.gamecodeschool.schoolutility;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pacificcollegiate.dialogs.DialogContact;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity
        implements MenubarFragment.OnFragmentInteractionListener {

    //Member variables//
    private ChildEventListener mUserChildEventListner;
    private ContactAdapter mContactAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    ////////////////////
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

       mFirebaseDatabase = FirebaseDatabase.getInstance();
       mUsersDatabaseReference = mFirebaseDatabase.getReference().child("users");

       List<Contact> contacts = new ArrayList<>();
       mContactAdapter = new ContactAdapter(this, R.layout.contact_listview, contacts);
       ListView listContacts = (ListView) findViewById(R.id.contact_listview_display);
       listContacts.setAdapter(mContactAdapter);

       listContacts.setOnItemClickListener(
               new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Contact tempContact = mContactAdapter.getItem(position);
                       DialogContact showContact = new DialogContact();
                       showContact.sendContact(tempContact);
                       showContact.show(getFragmentManager(), "");
                   }
               }
       );
    }

    @Override
    public void onResume() {
        super.onResume();
        attachDatabaseReadListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListners();
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
                Intent settingsIntent = new Intent(ContactsActivity.this, SettingActivity.class);
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
    public void onMenubarFragmentInteraction(int position) {
        //placeholder interaction listener which must be implemented
        //and can be filled with specific actions to be taken when clicked
    }

    public void attachDatabaseReadListener() {
        if (mUserChildEventListner == null) {
            mUserChildEventListner = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Contact contact = dataSnapshot.getValue(Contact.class);
                    mContactAdapter.add(contact);
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };

            mUsersDatabaseReference.addChildEventListener(mUserChildEventListner);
        }
    }

    public void detachDatabaseReadListners() {
        if (mUsersDatabaseReference != null) {
            mUsersDatabaseReference.removeEventListener(mUserChildEventListner);
            mUserChildEventListner = null;
        }
    }
}
