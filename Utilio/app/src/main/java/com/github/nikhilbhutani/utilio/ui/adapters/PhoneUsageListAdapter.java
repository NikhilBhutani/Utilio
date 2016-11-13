package com.github.nikhilbhutani.utilio.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.nikhilbhutani.utilio.R;

/**
 * Created by Nikhil Bhutani on 11/13/2016.
 */

public class PhoneUsageListAdapter extends RecyclerView.Adapter<PhoneUsageListAdapter.ViewHolder>{


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewphoneusagelist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView packageName;
        private final TextView lastTimeUsed;

        public ViewHolder(View itemView) {
            super(itemView);

            packageName = (TextView)itemView.findViewById(R.id.package_name);
            lastTimeUsed = (TextView)itemView.findViewById(R.id.lasttimeused);

        }

    }

}
