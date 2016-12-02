package com.github.nikhilbhutani.utilio.ui.adapters;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nikhilbhutani.utilio.Model.CustomUsageStats;
import com.github.nikhilbhutani.utilio.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nikhil Bhutani on 11/13/2016.
 */

public class PhoneUsageListAdapter extends RecyclerView.Adapter<PhoneUsageListAdapter.ViewHolder> {

    private List<CustomUsageStats> mCustomUsageStatsList = new ArrayList<>();
    private DateFormat mDateFormat = new SimpleDateFormat();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewphoneusagelist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.packageName.setText(mCustomUsageStatsList.get(position).usageStats.getPackageName());
            long lastTimeUsed = mCustomUsageStatsList.get(position).usageStats.getLastTimeUsed();
            holder.lastTimeUsed.setText(mDateFormat.format(new Date(lastTimeUsed)));

            holder.mAppIcon.setImageDrawable(mCustomUsageStatsList.get(position).appIcon);

        }

    }

    @Override
    public int getItemCount() {
        return mCustomUsageStatsList.size();
    }

    public void setCustomUsageStatsList(List<CustomUsageStats> customUsageStats) {
        mCustomUsageStatsList = customUsageStats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.package_name)
        TextView packageName;

        @BindView(R.id.lasttimeused)
        TextView lastTimeUsed;

        @BindView(R.id.app_icon)
        ImageView mAppIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
   //         packageName = (TextView) itemView.findViewById(R.id.package_name);
     //       lastTimeUsed = (TextView) itemView.findViewById(R.id.lasttimeused);
     //       mAppIcon = (ImageView) itemView.findViewById(R.id.app_icon);

        }

    }

}
