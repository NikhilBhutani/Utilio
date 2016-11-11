package com.github.nikhilbhutani.utilio.ui.fragments;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.nikhilbhutani.utilio.R;
import com.github.nikhilbhutani.utilio.ui.activities.DatadetailsActivity;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.USAGE_STATS_SERVICE;

/**
 * Created by Nikhil Bhutani on 10/11/2016.
 */

public class DashboardFragment extends Fragment {


    private Toolbar toolbar;
    private View view;
    private TextView dataUsed;
    private CardView dataCardView;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        dataUsed = (TextView)view.findViewById(R.id.DataUsed);
        dataCardView = (CardView)view.findViewById(R.id.cardview1);
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



}
