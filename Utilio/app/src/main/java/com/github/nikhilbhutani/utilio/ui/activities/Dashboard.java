package com.github.nikhilbhutani.utilio.ui.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.util.concurrent.TimeUnit;

import static android.R.attr.name;

/**
 * Created by Nikhil Bhutani on 10/9/2016.
 */

public class Dashboard extends BaseActivity implements OnDataPointListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    //  CardView cardView;
    private static final String TAG = Dashboard.class.getSimpleName();
    private static Tracker tracker;
    public static final String LOGTAG = "FitnessSensorApi";
    private static final int REQUEST_OAUTH = 1;
    private GoogleApiClient googleApiClient = null;
    private boolean authInProgress = false;
    private static final String AUTH_PENDING = "auth_state_pending";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new DashboardFragment())
                    .commit();


        }


        MyApplication myApplication = (MyApplication) getApplication();
        tracker = myApplication.getDefaultTracker();


        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


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
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "Setting screen name: " + name);
        tracker.setScreenName("Image~" + name);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        DataSourcesRequest dataSourcesRequest = new DataSourcesRequest.Builder()
                .setDataTypes(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .setDataSourceTypes(DataSource.TYPE_RAW)
                .build();


        ResultCallback<DataSourcesResult> dataSourcesResultCallback = new ResultCallback<DataSourcesResult>() {
            @Override
            public void onResult(@NonNull DataSourcesResult dataSourcesResult) {
                for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                    if (DataType.TYPE_STEP_COUNT_CUMULATIVE.equals(dataSource.getDataType())) {
                        registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE);
                    }
                }
            }
        };

        Fitness.SensorsApi.findDataSources(googleApiClient, dataSourcesRequest)
                .setResultCallback(dataSourcesResultCallback);


    }

    private void registerFitnessDataListener(DataSource dataSource, DataType typeStepCountCumulative) {

        SensorRequest request = new SensorRequest.Builder()
                .setDataSource(dataSource)
                .setDataType(typeStepCountCumulative)
                .setSamplingRate(3, TimeUnit.SECONDS)
                .build();

        Fitness.SensorsApi.add(googleApiClient, request, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.e("GoogleFit", "SensorApi successfully added");
                        }
                    }
                });

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


        if (!authInProgress) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult(Dashboard.this, REQUEST_OAUTH);
            } catch (IntentSender.SendIntentException e) {

            }
        } else {
            Log.e("GoogleFit", "authInProgress");
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                if (!googleApiClient.isConnecting() && !googleApiClient.isConnected()) {
                    googleApiClient.connect();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.e("GoogleFit", "RESULT_CANCELED");
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }


    @Override
    public void onDataPoint(DataPoint dataPoint) {

        for (final Field field : dataPoint.getDataType().getFields()) {
            final Value value = dataPoint.getValue(field);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        Fitness.SensorsApi.remove(googleApiClient, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            googleApiClient.disconnect();
                        }
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.disconnect();

    }


}
