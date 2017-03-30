package com.gamecodeschool.schoolutility;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity
        implements MenubarFragment.OnFragmentInteractionListener {
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

       //Placeholder code for demo
       LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.placeholderContactScroll);

       scrollLayout.setOnClickListener(
               new View.OnClickListener()
               {
                   @Override
                   public void onClick(View v)
                   {
                        DialogContact viewContact = new DialogContact();
                        viewContact.show(getFragmentManager(), "");
                   }
               }
       );
    }

    @Override
    public void onFragmentInteraction(int position)
    {
        //placeholder interaction listener which must be implemented
        //and can be filled with specific actions to be taken when clicked
    }

    //Implements ListView for contacts listview
    public class ContactsAdapter extends BaseAdapter
    {
        List<Contact> contactList = new ArrayList<Contact>();

        @Override
        public int getCount()
        {
            return contactList.size();
        }

        @Override
        public long getItemId(int whichItem)
        {
            return whichItem;
        }

        @Override
        public Contact getItem(int whichItem)
        {
            return contactList.get(whichItem);
        }

        //Implements the ListView
        @Override
        public View getView(int whichItem, View view, ViewGroup viewGroup)
        {
            //Inflates if it hasn't already been done
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.contact_listview, viewGroup, false);
            }

            //Create a reference to the assignment in question
            Contact tempContact = contactList.get(whichItem);

            TextView contactName = (TextView) view.findViewById(R.id.contactName);
            TextView contactInformation = (TextView) view.findViewById(R.id.contactInformation);
            QuickContactBadge contactBadge = (QuickContactBadge) view.findViewById(R.id.contactBadge);

            contactName.setText(tempContact.getmName());
            contactInformation.setText(tempContact.getmInformation());
            //TODO: Implement something which chanegs contact badges. This will also probably have to be tied in with account creeation

            return view;
        }

        //Method currently never called, but will be needed to add contacts to the list
        public void addContact(Contact c)
        {
            contactList.add(c);
            notifyDataSetChanged();
        }
    }
}
