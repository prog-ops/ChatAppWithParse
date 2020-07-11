package com.chat.prs;

import android.app.Application;

import com.parse.Parse;
import com.parse.Parse.Configuration;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // ngarang bisa
        Parse.initialize(new Configuration
                .Builder(this)
                .applicationId("9tvevK7kHRthCJFlyFFMZIkjL62s33ModYXpuz6K")
                .clientKey("h275fYRTAXjQIjc9noqYWFcACXu1mi26qBxnUv8x")
                .build());

        // tutorial, deprecated
        /*Parse.initialize(
                this,
                "9tvevK7kHRthCJFlyFFMZIkjL62s33ModYXpuz6K",
                "h275fYRTAXjQIjc9noqYWFcACXu1mi26qBxnUv8x");*/
    }
}
