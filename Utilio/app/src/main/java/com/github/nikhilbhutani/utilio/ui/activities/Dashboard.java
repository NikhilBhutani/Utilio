package com.github.nikhilbhutani.utilio.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.nikhilbhutani.utilio.R;
import com.github.nikhilbhutani.utilio.ui.fragments.DashboardFragment;

/**
 * Created by Nikhil Bhutani on 10/9/2016.
 */

public class Dashboard extends BaseActivity {

  //  CardView cardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new DashboardFragment())
                    .commit();

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
}
