package com.remindyou.remindyou;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    public void onCreate() {

        super.onCreate();

        // Start Parse
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.PARSE_APPLICATION_ID), getString(R.string.PARSE_CLIENT_KEY));
        // End Parse
    }
}