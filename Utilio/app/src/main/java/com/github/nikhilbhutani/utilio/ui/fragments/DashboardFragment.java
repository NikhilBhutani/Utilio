package com.github.nikhilbhutani.utilio.ui.fragments;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nikhilbhutani.utilio.R;
import com.github.nikhilbhutani.utilio.ui.activities.Dashboard;
import com.github.nikhilbhutani.utilio.ui.activities.DatadetailsActivity;
import com.github.nikhilbhutani.utilio.ui.activities.PhonedetailsActivity;
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

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.USAGE_STATS_SERVICE;

/**
 * Created by Nikhil Bhutani on 10/11/2016.
 */

public class DashboardFragment extends Fragment implements OnDataPointListener, GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener{


    private Toolbar toolbar;
    private View view;
    private TextView dataUsed;
    private CardView dataCardView;
    private CardView phoneUsageCardView;
    public static final String LOGTAG = "FitnessSensorApi";
    private static final int REQUEST_OAUTH = 1;
    private GoogleApiClient googleApiClient = null;
    private boolean authInProgress = false;
    private static final String AUTH_PENDING = "auth_state_pending";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                            .addApi(Fitness.SENSORS_API)
                            .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();

    }

    @Override
    public void onStart() {
        super.onStart();

        googleApiClient.connect();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        dataUsed = (TextView)view.findViewById(R.id.DataUsed);
        dataCardView = (CardView)view.findViewById(R.id.cardview1);
        phoneUsageCardView = (CardView)view.findViewById(R.id.cardview2);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        displayDataUsed();

        displayPhoneUsage();

        dataCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   getActivity().startActivity(new Intent(getContext(), DatadetailsActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });


        phoneUsageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getContext(), PhonedetailsActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
    }

    private void displayDataUsed() {
        long usedData = TrafficStats.getTotalTxBytes();

        if((usedData/1024) > 1000){
            dataUsed.setText(String.valueOf(usedData/(1024*1024))+" mb");
        }else{
            dataUsed.setText(String.valueOf(usedData/1024)+" kb");
        }


    }

    private void displayPhoneUsage() {

        UsageStatsManager statsManager = (UsageStatsManager)getContext().getSystemService(USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();





        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            List<UsageStats> stats = statsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 60, time);

            Iterator<UsageStats> iterator = stats.iterator();

            if(iterator.hasNext()){
                Object o = iterator.next();
                    System.out.println("My usage stats is : "+o);
                }
            }

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
                for(DataSource dataSource : dataSourcesResult.getDataSources())
                {
                    if(DataType.TYPE_STEP_COUNT_CUMULATIVE.equals(dataSource.getDataType()))
                    { registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE);}
                }
            }
        };

        Fitness.SensorsApi.findDataSources(googleApiClient, dataSourcesRequest)
                .setResultCallback(dataSourcesResultCallback);

    }


    private void registerFitnessDataListener(DataSource dataSource, DataType typeStepCountCumulative) {

        SensorRequest request = new SensorRequest.Builder()
                .setDataSource( dataSource )
                .setDataType(typeStepCountCumulative )
                .setSamplingRate( 3, TimeUnit.SECONDS )
                .build();

        Fitness.SensorsApi.add( googleApiClient, request, this )
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.e( "GoogleFit", "SensorApi successfully added" );
                        }
                    }
                });

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
     public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if( !authInProgress ) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult(getActivity(), REQUEST_OAUTH );
            } catch(IntentSender.SendIntentException e ) {

            }
        } else {
            Log.e( "GoogleFit", "authInProgress" );
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_OAUTH ) {
            authInProgress = false;
            if( resultCode == RESULT_OK ) {
                if( !googleApiClient.isConnecting() && !googleApiClient.isConnected() ) {
                    googleApiClient.connect();
                }
            } else if( resultCode == RESULT_CANCELED ) {
                Log.e( "GoogleFit", "RESULT_CANCELED" );
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }


    @Override
    public void onDataPoint(DataPoint dataPoint) {

        for( final Field field : dataPoint.getDataType().getFields() ) {
            final Value value = dataPoint.getValue( field );
           getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  //  Toast.makeText(, "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        Fitness.SensorsApi.remove( googleApiClient, this )
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        googleApiClient.disconnect();

    }
}
