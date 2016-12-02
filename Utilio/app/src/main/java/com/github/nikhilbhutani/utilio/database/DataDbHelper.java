package com.github.nikhilbhutani.utilio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nikhil Bhutani on 10/24/2016.
 */

public class DataDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "utilio.db";
    //If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    public DataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create table to hold application data
        final String SQL_CREATE_APPDATA_TABLE = "CREATE TABLE " + DataContract.ApplicationData.TABLE_NAME + " (" +
                DataContract.ApplicationData._ID + " INTEGER PRIMARY KEY," +
                DataContract.ApplicationData.COLUMN_APPNAME + " TEXT NOT NULL, " +
                DataContract.ApplicationData.COLUMN_DATA_RECEIVED + " REAL, " +
                DataContract.ApplicationData.COLUMN_DATA_TRANSMITTED + " REAL" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_APPDATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newversion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataContract.ApplicationData.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
