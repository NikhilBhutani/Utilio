package com.github.nikhilbhutani.utilio.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by Nikhil Bhutani on 10/24/2016.
 */

public class DataContract {

    //The "Content authority" is a name for entire content provider, similar to the relationship
    //between a domain name and its website. A convenient string to use for the content authority is the
    //package name for the app, which is guaranteed to be unique on the device.

    public static final String CONTENT_AUTHORITY = "com.github.nikhilbhutani.utilio";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Possible Paths (appended to base content URI for possible URI's)
    //For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    //looking at weather data.


    public static final String PATH_APPDATA = "applicationdata";


    // Inner class that defines the table contents

    public static final class ApplicationData implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_APPDATA).build();

        public static final String CONTENT_TYPE  =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_APPDATA;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_APPDATA;

        //Table Name
        public static final String TABLE_NAME = "applications";

        //Columns
        public static final String COLUMN_APPNAME = "app_name";
        public static final String COLUMN_DATA_RECEIVED = "data_received";
        public static final String COLUMN_DATA_TRANSMITTED = "data_transmitted";


       public static Uri buildApplicationUri(long id){
           return ContentUris.withAppendedId(CONTENT_URI, id);
       }
    }
}
