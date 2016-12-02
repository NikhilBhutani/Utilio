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

import butterknife.BindView;
import butterknife.ButterKnife;

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
        holder.dataReceived.setText(mCursor.getString(mCursor.getColumnIndex(DataContract.ApplicationData.COLUMN_DATA_RECEIVED)));
        holder.dataTransmitted.setText(mCursor.getString(mCursor.getColumnIndex(DataContract.ApplicationData.COLUMN_DATA_TRANSMITTED)));
    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

         @BindView(R.id.app_name)
         TextView appName;

         @BindView(R.id.downloaded_data)
         TextView dataReceived;

         @BindView(R.id.uploaded_data)
         TextView dataTransmitted;

        public MyViewHolder(View itemView) {
            super(itemView);

         //   appName = (TextView) itemView.findViewById(R.id.app_name);
         //   dataReceived = (TextView) itemView.findViewById(R.id.downloaded_data);
          //  dataTransmitted = (TextView) itemView.findViewById(R.id.uploaded_data);


            ButterKnife.bind(this, itemView);
        }
    }
}
