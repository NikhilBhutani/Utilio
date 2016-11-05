package com.github.nikhilbhutani.utilio.ui.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.nikhilbhutani.utilio.R;
import com.github.nikhilbhutani.utilio.database.DataContract;
import com.github.nikhilbhutani.utilio.ui.adapters.AppDataRecyclerViewAdapter;

/**
 * Created by Nikhil Bhutani on 10/11/2016.
 */

public class DataDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int APPDATA_LOADER = 0;

    RecyclerView recyclerView;
    AppDataRecyclerViewAdapter recyclerViewAdapter;

    private static final String[] APPDATA_COLUMNS = {
            DataContract.ApplicationData.TABLE_NAME + "." + DataContract.ApplicationData._ID,
            DataContract.ApplicationData.COLUMN_APPNAME,
            DataContract.ApplicationData.COLUMN_DATA_RECEIVED,
            DataContract.ApplicationData.COLUMN_DATA_TRANSMITTED
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_networkdetail, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewDataItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(APPDATA_LOADER, null, this);


        //  System.out.println("IN FRAGMENT" +mCursor.getString(mCursor.getColumnIndex(DataContract.ApplicationData.COLUMN_APPNAME)));


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //This is only called when new Loader needs to be created.
        // Sort order:  Ascending, by Application Name.
        // String sortOrder = DataContract.ApplicationData.COLUMN_APPNAME + " ASC";

        return new CursorLoader(getActivity(),
                DataContract.ApplicationData.CONTENT_URI,
                APPDATA_COLUMNS,
                null,
                null,
                null
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        data.moveToFirst();
        recyclerViewAdapter = new AppDataRecyclerViewAdapter(getActivity(), data);
        recyclerView.setAdapter(recyclerViewAdapter);

        /*
        while (!data.isAfterLast()){

         //   data.getString(data.getColumnIndex(DataContract.ApplicationData.COLUMN_APPNAME));
              System.out.println( data.getString(data.getColumnIndex(DataContract.ApplicationData.COLUMN_APPNAME)));
              System.out.println( data.getString(data.getColumnIndex(DataContract.ApplicationData.COLUMN_DATA_RECEIVED)));
              System.out.println( data.getString(data.getColumnIndex(DataContract.ApplicationData.COLUMN_DATA_TRANSMITTED)));

            data.moveToNext();
        }*/

        // System.out.println("IN FRAGMENT" +data.getString(data.getColumnIndex(DataContract.ApplicationData.COLUMN_APPNAME)));


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
