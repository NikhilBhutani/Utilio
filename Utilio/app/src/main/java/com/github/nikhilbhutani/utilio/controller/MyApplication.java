package com.github.nikhilbhutani.utilio.controller;

import android.app.Application;
import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.AsyncTask;

import com.facebook.stetho.Stetho;
import com.github.nikhilbhutani.utilio.database.DataContract;

import java.util.ArrayList;
import java.util.Vector;

import static java.security.AccessController.getContext;

/**
 * Created by Nikhil Bhutani on 10/21/2016.
 */

public class MyApplication extends Application {

    public static final String LOGTAG = MyApplication.class.getSimpleName();
    ArrayList<ApplicationInfo> appInfoList;
    Vector<ContentValues> cVVector;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        final PackageManager packageManager = getApplicationContext().getPackageManager();

        System.out.println("MY APPLICATION's ONCREATE CALLED");


        new AsyncTask<Void, Void, Void>(){


            @Override
            protected Void doInBackground(Void... voids) {

                appInfoList = (ArrayList<ApplicationInfo>) packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

                 cVVector = new Vector<ContentValues>();

                ContentValues contentValues = new ContentValues();

                for(ApplicationInfo applicationInfo : appInfoList){

                    String appname = (String)packageManager.getApplicationLabel(applicationInfo);
                    double received = ((double) TrafficStats.getUidRxBytes(applicationInfo.uid));
                    double transmitted = ((double) TrafficStats.getUidTxBytes(applicationInfo.uid));

                    if(received!=0) {

                        double receivedInKb = received/1024;
                        double transmittedInKb = transmitted/1024;

                        contentValues.put(DataContract.ApplicationData.COLUMN_APPNAME, appname);
                        contentValues.put(DataContract.ApplicationData.COLUMN_DATA_RECEIVED, receivedInKb);
                        contentValues.put(DataContract.ApplicationData.COLUMN_DATA_TRANSMITTED, transmittedInKb);
                        cVVector.add(contentValues);
                    }
                }

                getApplicationContext().getContentResolver().insert(DataContract.ApplicationData.CONTENT_URI, contentValues);
              /*
                int inserted = 0;
                // add to database
                if (cVVector.size() > 0) {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);

                }
               */
                    return null;
            }
        }.execute();


    }


}
