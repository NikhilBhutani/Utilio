package com.github.nikhilbhutani.utilio.controller;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.AsyncTask;

import com.github.nikhilbhutani.utilio.R;
import com.github.nikhilbhutani.utilio.database.DataContract;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

/**
 * Created by Nikhil Bhutani on 10/21/2016.
 */

public class MyApplication extends Application {

    public static final String LOGTAG = MyApplication.class.getSimpleName();
    ArrayList<ApplicationInfo> appInfoList;
  //  Vector<ContentValues> cVVector;
    private String appname;
    private double received;
    private double transmitted;
    private double receivedInKb;
    private double transmittedInKb;
    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        /*
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
              */

        final PackageManager packageManager = getApplicationContext().getPackageManager();

        System.out.println("MY APPLICATION's ONCREATE CALLED");


        new AsyncTask<Void, Void, Void>(){


            @Override
            protected Void doInBackground(Void... voids) {

                appInfoList = (ArrayList<ApplicationInfo>) packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

               //  cVVector = new Vector<ContentValues>();

                ContentValues contentValues = new ContentValues();

                for(ApplicationInfo applicationInfo : appInfoList){


                    appname = (String)packageManager.getApplicationLabel(applicationInfo);
                    received = ((double) TrafficStats.getUidRxBytes(applicationInfo.uid));
                    transmitted = ((double) TrafficStats.getUidTxBytes(applicationInfo.uid));

                    System.out.println(" App name " + appname+ " Data Received : "+received+ " Data Transmitted "+transmitted+ "UID "+applicationInfo.uid);


                    if(received!=0) {
                         receivedInKb = received / 1024;
                         transmittedInKb = transmitted / 1024;

                         contentValues.put(DataContract.ApplicationData.COLUMN_APPNAME, appname);
                         // Displaying Application names on the console.
                         contentValues.put(DataContract.ApplicationData.COLUMN_DATA_RECEIVED, receivedInKb);
                         contentValues.put(DataContract.ApplicationData.COLUMN_DATA_TRANSMITTED, transmittedInKb);
                         getApplicationContext().getContentResolver().insert(DataContract.ApplicationData.CONTENT_URI, contentValues);
                     }

                }

                    return null;
            }
        }.execute();



    }


    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

}
