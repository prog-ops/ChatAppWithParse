package com.chat.prs;

import android.app.Application;

import com.parse.Parse;
import com.parse.Parse.Configuration;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //* https://guides.codepath.com/android/Building-Data-driven-Apps-with-Parse

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        //* Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        //* set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        /*Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myAppId") // should correspond to APP_ID env variable
                .clientKey(null)  // set explicitly unless clientKey is explicitly configured on Parse server
                .clientBuilder(builder)
                .server("https://my-parse-app-url.herokuapp.com/parse/")
                .build());
*/

        // ngarang bisa
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("9tvevK7kHRthCJFlyFFMZIkjL62s33ModYXpuz6K")
                .clientKey("h275fYRTAXjQIjc9noqYWFcACXu1mi26qBxnUv8x")
                .clientBuilder(builder)
                .server("https://parseapi.back4app.com/")
                .build()
        );

        // New test creation of object below
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        // tutorial, deprecated
        /*Parse.initialize(
                this,
                "9tvevK7kHRthCJFlyFFMZIkjL62s33ModYXpuz6K",
                "h275fYRTAXjQIjc9noqYWFcACXu1mi26qBxnUv8x");*/
    }
}
