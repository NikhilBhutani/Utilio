package com.github.nikhilbhutani.utilio.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.nikhilbhutani.utilio.R;
import com.github.nikhilbhutani.utilio.ui.fragments.DataDetailsFragment;

/**
 * Created by Nikhil Bhutani on 10/11/2016.
 */

public class DatadetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networkdetail);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.networkdetail_container, new DataDetailsFragment())
                    .commit();
        }


    }

}
