package com.gamecodeschool.schoolutility;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
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
    //TODO: URGENT: Make placeholder layouts for all the pages which will eventually be implemented
    //TODO: URGENT: due-date textview and actual due-date often are placed over each other, something needs to be modified in the layout file
    //TODO: URGENT: Go through the layouts and make some of the weird off grey text black
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



    //Member variables//
    private HomeworkAdapter mHomeworkAdapter;
    ////////////////////

    public void onFragmentInteraction(int position) {
        //Empty method which can be filled with specific actions for the menu bar to take
        //Must be present for the menubar to work
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHomeworkAdapter = new HomeworkAdapter();

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
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mHomeworkAdapter.saveAssignments();
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

        if (id==R.id.addArticleDropdown)
        {
            DialogAddArticle newArticle = new DialogAddArticle();
            newArticle.show(getFragmentManager(), null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createHomeworkAssignment(HomeworkAssignment h)
    {
        mHomeworkAdapter.addAssignment(h);
    }


    //Implements ListView and JSONSerializer for HomeworkAssignments and the homework listview
    public class HomeworkAdapter extends BaseAdapter
    {
        List<HomeworkAssignment> homeworkList = new ArrayList<HomeworkAssignment>();
        private JSONSerializer mSerializer;

        public HomeworkAdapter()
        {
            mSerializer = new JSONSerializer("PumaPlanner.json", MainActivity.this.getApplicationContext());

            try {
                homeworkList = mSerializer.load();
            }
            catch(Exception e) {
                homeworkList = new ArrayList<HomeworkAssignment>();
                Log.e("Error loading notes: ", "", e);
            }
        }

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

        @Override
        public HomeworkAssignment getItem(int whichItem)
        {
            return homeworkList.get(whichItem);
        }

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
            //TODO: Make it so that the imageview varies based upon class type or assignment type

            return view;
        }

        public void addAssignment(HomeworkAssignment h)
        {
            homeworkList.add(h);
            notifyDataSetChanged();
        }

        public void saveAssignments()
        {
            try {
                mSerializer.saveHomework(homeworkList);
            }
            catch (Exception e) {
                Log.e("Error saving notes: ", "", e);
            }
        }
    }

}
