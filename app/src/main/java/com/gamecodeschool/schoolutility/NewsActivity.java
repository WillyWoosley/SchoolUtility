package com.gamecodeschool.schoolutility;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.firebase.ui.auth.AuthUI;

import java.net.URI;

public class NewsActivity extends AppCompatActivity
    implements MenubarFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //TODO: Replace this with something that will not just redirect to the ROAR homepage
        //WebView testWeb = (WebView) findViewById(R.id.testWebView);
        //testWeb.loadUrl("http://www.pcsroar.com/");
        Uri uri = Uri.parse("http://www.pcsroar.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
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
                Intent settingsIntent = new Intent(NewsActivity.this, SettingActivity.class);
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
}
