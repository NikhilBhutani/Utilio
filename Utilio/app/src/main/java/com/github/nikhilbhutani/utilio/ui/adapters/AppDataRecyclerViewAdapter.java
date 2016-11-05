package com.github.nikhilbhutani.utilio.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.nikhilbhutani.utilio.R;
import com.github.nikhilbhutani.utilio.database.DataContract;

/**
 * Created by Nikhil Bhutani on 11/3/2016.
 */

public class AppDataRecyclerViewAdapter extends RecyclerView.Adapter<AppDataRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private Cursor mCursor;

    public AppDataRecyclerViewAdapter(Context context, Cursor cursor) {

        this.context = context;
        this.mCursor = cursor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewdataitemlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        holder.appName.setText(mCursor.getString(mCursor.getColumnIndex(DataContract.ApplicationData.COLUMN_APPNAME)));

    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView appName;

        public MyViewHolder(View itemView) {
            super(itemView);

            appName = (TextView) itemView.findViewById(R.id.app_name);
        }
    }
}
