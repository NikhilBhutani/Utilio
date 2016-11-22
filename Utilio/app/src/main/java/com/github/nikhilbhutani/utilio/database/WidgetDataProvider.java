package com.github.nikhilbhutani.utilio.database;

import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by Nikhil Bhutani on 11/22/2016.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
