package com.gamecodeschool.schoolutility;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URI;

public class NewsActivity extends AppCompatActivity
    implements MenubarFragment.OnFragmentInteractionListener {

    public WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mWebView = (WebView) findViewById(R.id.newsWebView);
        mWebView.loadUrl("http://www.pcsroar.com/");
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onMenubarFragmentInteraction(int position) {
        //placeholder interaction listener which must be implemented
        //and can be filled with specific actions to be taken when clicked
    }
}
