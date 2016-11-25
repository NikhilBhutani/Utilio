package com.github.nikhilbhutani.utilio.ui.activities;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.widget.RemoteViews;

import com.github.nikhilbhutani.utilio.R;

/**
 * Created by Nikhil Bhutani on 11/22/2016.
 */

public class WidgetActivity extends AppWidgetProvider {

    long usedData;

    // Called for every update of the widget
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }


    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        usedData = TrafficStats.getTotalTxBytes();


        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.utility_widget);

        if ((usedData / 1024) > 1000) {
            remoteViews.setTextViewText(R.id.datausedwidget_text, String.valueOf(usedData / (1024 * 1024)) + " mb");
        } else {
            remoteViews.setTextViewText(R.id.datausedwidget_text, String.valueOf(usedData / 1024) + " kb");
        }

        Intent intent = new Intent(context, WidgetActivity.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        //setRemoteAdapterV11(context, remoteViews);

    }


    //Called for the first time an instance  of your widget is added to the home screen
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }


    //Called once the last instance of your widget is removed from the home screen.
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

}
