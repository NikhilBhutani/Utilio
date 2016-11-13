package com.github.nikhilbhutani.utilio.ui.fragments;

import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.github.nikhilbhutani.utilio.R;
import com.github.nikhilbhutani.utilio.ui.adapters.PhoneUsageListAdapter;

/**
 * Created by Nikhil Bhutani on 10/11/2016.
 */

public class PhoneDetailsFragment extends Fragment{


    UsageStatsManager usageStatsManager;
    PhoneUsageListAdapter phoneUsageListAdapter;
    RecyclerView recyclerView;
    Button button;
    Spinner spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usageStatsManager = (UsageStatsManager)getActivity().getSystemService(Context.USAGE_STATS_SERVICE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phoneusagedetails, container, false);
        phoneUsageListAdapter = new PhoneUsageListAdapter();
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewPhoneusage);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
