package com.github.nikhilbhutani.utilio.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor = null;

        //Here is the switch statement that, given a URI, will determine what kind of request it is,
        //and query the database accordingly

        switch (mUriMatcher.match(uri)){

            case APPDATA:
            {
                retCursor = dataDbHelper.getReadableDatabase().query(
                  DataContract.ApplicationData.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;
            }

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
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

        final SQLiteDatabase db = dataDbHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);

        Uri returnUri = null;

        switch(match){

            case APPDATA: {

                long _id = db.insert(DataContract.ApplicationData.TABLE_NAME, null, contentValues);
                if(_id>0){

                    returnUri = DataContract.ApplicationData.buildApplicationUri(_id);
                }
                else
                {throw new android.database.SQLException("Failed to insert row into" +uri); }

                break;
            }
            
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        final SQLiteDatabase sqLiteDatabase = dataDbHelper.getWritableDatabase();

        final int match = mUriMatcher.match(uri);

        switch (match){
            case APPDATA :
                sqLiteDatabase.beginTransaction();
                int returnCount = 0;

                try {
                    for(ContentValues value : values){

                        long _id = sqLiteDatabase.insert(DataContract.ApplicationData.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                  sqLiteDatabase.setTransactionSuccessful();
                }finally {
                    sqLiteDatabase.endTransaction();
                }

                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:

                return super.bulkInsert(uri, values);
        }

    }
}
