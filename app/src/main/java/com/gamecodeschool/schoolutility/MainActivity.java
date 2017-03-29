package com.gamecodeschool.schoolutility;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements MenubarFragment.OnFragmentInteractionListener {


    //General to-do's
    //TODO: URGENT: Need to implement JSONSerialization for tomorrow, homewokr views disappear when you click to a different activity since they aren't being saved anywhere
    //TODO: Get contact working, with placeholder contacts and ideally a working searchbar, assuming that google already has a pretty well made searchbar provided
    //TODO: Make the article assign dialog appear, and actually do something more than appear (save the information somewhere)
    //TODO: Make all data persistent locally for now
    //TODO: Make events work. Not sure how this is going to look/work, not pressing for the demo
    //TODO: Make the contacts activity layout
    //TODO: Make the events activity layout
    //TODO: Make the news page layout
    //TODO: Make a settings page
    //TODO: Fill in the main activity layout
    //TODO: Make the homework page layout
    //TODO: Make a placeholder image to be contained in the listview if there is no homework the user currently has assigned/to do
    //TODO: Add ability to check off homework assignments from the list to set them as complete
    //TODO: Make the homework listview items images/background vary based upon assignment type/class
    //TODO: Fill in menubar placeholder assets with real images

    //Completed:
    //TODO: Add mainActivity to menubar

    //Member variables
    HomeworkAssignment mPassedHomework = new HomeworkAssignment();
    private HomeworkAdapter mHomeworkAdapter;
    //

    public void onFragmentInteraction(int position)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHomeworkAdapter = new HomeworkAdapter();

        //References to bottom action bar options
        ImageButton newsSwap = (ImageButton) findViewById(R.id.mainNews);
        ImageButton contactsSwap = (ImageButton) findViewById(R.id.mainContacts);
        ImageButton homeworkSwap = (ImageButton) findViewById(R.id.mainHomework);
        ImageButton eventsSwap = (ImageButton) findViewById(R.id.mainEvents);

        //Intent to switch to HomeworkActivity if button or ListView is clicked
        final Intent homeworkIntent = new Intent(this, HomeworkActivity.class);

        //Creates reference to homework ListView and sets the adapter to the member adapter
        ListView listAssignment = (ListView) findViewById(R.id.homework_listview_display);
        listAssignment.setAdapter(mHomeworkAdapter);

        listAssignment.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Creates reference to clicked assignment
                        HomeworkAssignment tempAssignment = mHomeworkAdapter.getItem(position);
                        //Then creates show dialog, passes assignment reference, and inflates
                        DialogShowHomework showHomework = new DialogShowHomework();
                        showHomework.sendHomework(tempAssignment);
                        showHomework.show(getFragmentManager(), "");
                    }
                }
        );

        //placeholder button to check functionality of homework assigning
        Button assignmentShow = (Button) findViewById(R.id.placeholder_retrieve);

        final Intent testIntent = new Intent(this, HomeworkActivity.class);

        assignmentShow.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                       startActivity(testIntent);
                    }
                }
        );

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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the admin menu
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //Get reference to pressed option
        int id = item.getItemId();

        if (id==R.id.settingBar)
        {
            //insert code to get to a settings page which will eventually be added
            return true;
        }

        if (id==R.id.addAssignmentDropdown)
        {
            //Creates a new DialogAssignHomework and displays it
            DialogAssignHomework newAssignment = new DialogAssignHomework();
            newAssignment.show(getFragmentManager(), "");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createHomeworkAssignment(HomeworkAssignment h)
    {
        mHomeworkAdapter.addAssignment(h);
    }

    //Class which will be filled with the details of how to serialize once
    //JSONSerializer is built, and currently implements ListView
    public class HomeworkAdapter extends BaseAdapter
    {
        //Right now only contains homeworkList and mandatoyr methods
        List<HomeworkAssignment> homeworkList = new ArrayList<HomeworkAssignment>();

        @Override
        public int getCount()
        {
            return homeworkList.size();
        }

        @Override
        public long getItemId(int whichItem)
        {
            return whichItem;
        }

        public HomeworkAssignment getItem(int whichItem)
        {
            return homeworkList.get(whichItem);
        }


        //Class which implements the Homework ListView, will change once
        //I figure out the seemingly trivial but nigh impossible task
        //of making it scroll horizontally rather than vertically
        @Override
        public View getView(int whichItem, View view, ViewGroup viewGroup)
        {
            //Inflates view if it hasn't been
            if (view==null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.homework_listview, viewGroup, false);
            }

            //Create a reference to the assignment to be referenced
            HomeworkAssignment tempAssignment = homeworkList.get(whichItem);

            //Create references to all pertinent areas of layout
            TextView assignmentName = (TextView) view.findViewById(R.id.assignment_name_display);
            TextView dueDate = (TextView) view.findViewById(R.id.due_date_display);
            ImageView assignmentImage = (ImageView) view.findViewById(R.id.assignment_imageView);

            //Set the date, name, and image
            assignmentName.setText(tempAssignment.getAssignmentName());
            dueDate.setText(tempAssignment.getDueDate());
            //This will eventually make it so the image changes as well
            //assignmentImage.setImageIcon();

            return view;
        }

        public void addAssignment(HomeworkAssignment h)
        {
            homeworkList.add(h);
            notifyDataSetChanged();
        }
    }

}
