package com.github.nikhilbhutani.utilio.ui.fragments;

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


        long usedData = TrafficStats.getTotalTxBytes();

        if((usedData/1024) > 1000){
            dataUsed.setText(String.valueOf(usedData/(1024*1024))+" mb");
        }else{
            dataUsed.setText(String.valueOf(usedData/1024)+" kb");
        }


        dataCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   getActivity().startActivity(new Intent(getContext(), DatadetailsActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
    }

}
