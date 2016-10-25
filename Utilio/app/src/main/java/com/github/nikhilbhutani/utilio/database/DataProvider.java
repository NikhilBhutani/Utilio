package com.github.nikhilbhutani.utilio.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Nikhil Bhutani on 10/24/2016.
 */

public class DataProvider extends ContentProvider{

   //The URI matcher used by this content provider.
    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private DataDbHelper dataDbHelper;
    static final int APPDATA = 100;


    private static UriMatcher buildUriMatcher() {

      final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

         final String authority = DataContract.CONTENT_AUTHORITY;

        //For specific Uri we want to add, create a corresponding code

        uriMatcher.addURI(authority, DataContract.PATH_APPDATA, APPDATA);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        dataDbHelper = new DataDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        Cursor retCursor;

        //Here is the switch statement that, given a URI, will determine what kind of request it is,
        //and query the database accordingly

        switch (mUriMatcher.match(uri)){

            case APPDATA:

        }

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
