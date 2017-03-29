package com.gamecodeschool.schoolutility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class NewsActivity extends AppCompatActivity
    implements MenubarFragment.OnFragmentInteractionListener {

    //Placeholder activity for NewsActivity, here to get menubar working
    //Use that webview that displays javascript objects from external webpages
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        WebView testWeb = (WebView) findViewById(R.id.testWebView);
        testWeb.loadUrl("http://www.tutorialspoint.com");
    }

    @Override
    public void onFragmentInteraction(int position)
    {
        //placeholder interaction listener which must be implemented
        //and can be filled with specific actions to be taken when clicked
    }
}
