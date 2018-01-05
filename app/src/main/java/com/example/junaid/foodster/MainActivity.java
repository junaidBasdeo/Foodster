package com.example.junaid.foodster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;


import com.example.junaid.foodster.Globals.ApplicationGlobals;

public class MainActivity extends BaseActivity {

    private ApplicationGlobals ag = ApplicationGlobals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // only show login screen if the user session is inactive
        // since not using real oauth this would in theory alwasy be
        // false if the user exited the app and re-opened for v1
        if (ag.getUserHasLoggedIn())
        {
            Intent i = new Intent(this, YelpSearchActivity.class);
            startActivity(i);
        } else {

            setContentView(R.layout.activity_main);

            WebView forumView= findViewById(R.id.getattention);
            forumView.setWebViewClient(new WebViewController());
            forumView.getSettings().setJavaScriptEnabled(true);
            forumView.loadUrl("http://www.cafedutrocadero.com/");


        }
    }
}

