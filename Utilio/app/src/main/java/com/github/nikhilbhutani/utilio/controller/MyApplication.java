package com.github.nikhilbhutani.utilio.controller;

import android.app.Application;
import android.os.AsyncTask;

/**
 * Created by Nikhil Bhutani on 10/21/2016.
 */

public class MyApplication extends Application {

    public static final String LOGTAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        new AsyncTask<Void, Void, Void>(){


            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }
        }.execute();


    }


}
