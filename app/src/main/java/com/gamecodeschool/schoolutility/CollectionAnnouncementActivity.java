/*package com.gamecodeschool.schoolutility;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CollectionAnnouncementActivity extends FragmentActivity {
    //Member Variable//
    ViewPager mViewPager;
    ///////////////////

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_announcement);

        //Create the adapter that will return a fragment representing an object in the collection

    }

    public static class AnnouncementCollectionPagerAdapter extends FragmentStatePagerAdapter
    {
        public  AnnouncementCollectionPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int i)
        {
            Fragment fragment = new DemoObjectFragment();

        }
    }

    public static class AnnouncementObjectFragment extends Fragment
    {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {
            //This needs to be a fragment
            View rootView = inflater.inflate(R.layout.activity_collection_announcement);
        }
    }
}
*/