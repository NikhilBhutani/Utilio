package com.github.nikhilbhutani.utilio.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.nikhilbhutani.utilio.R;
import com.github.nikhilbhutani.utilio.controller.MyApplication;
import com.github.nikhilbhutani.utilio.ui.fragments.DashboardFragment;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import static android.R.attr.name;

/**
 * Created by Nikhil Bhutani on 10/9/2016.
 */

public class Dashboard extends BaseActivity {

  //  CardView cardView;
    private static final String TAG = Dashboard.class.getSimpleName();
    private static Tracker tracker;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new DashboardFragment())
                    .commit();

            MyApplication myApplication = (MyApplication)getApplication();
            tracker = myApplication.getDefaultTracker();


        }



/*
       toolbar = (Toolbar)findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        cardView = (CardView)findViewById(R.id.cardview1);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Hey Clicked man", Toast.LENGTH_SHORT).show();
            }
        });
      */
    }



    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "Setting screen name: " + name);
        tracker.setScreenName("Image~" + name);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

}
