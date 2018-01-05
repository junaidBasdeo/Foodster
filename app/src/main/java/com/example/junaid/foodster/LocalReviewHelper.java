package com.example.junaid.foodster;

// sources
// http://stacktips.com/tutorials/android/android-sqlite-database-tutorial

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class LocalReviewHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "LOCALREVIEWS";

    // Table columns
    public static final String ID = "id";
    public static final String BUSINESS_YELP_ID = "business_yelp_id";
    public static final String USER_NAME = "localreviewer";
    public static final String USER_RATING = "localrating";
    public static final String USER_TEXT = "localtext";
    public static final String USER_DATE = "date";


    // Database Information
    static final String DB_NAME = "users.db";

    // database version
    static final int DB_VERSION = 1;

    public  boolean doesDatabaseExist(Context passedContext) {
        File dbFile = passedContext.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    public  boolean doesTableExist(LocalReviewController db) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_NAME + "'");

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        // doesn't exist so try to create it.....
        try {
            db.rawQuery(CREATE_TABLE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // Creating table query
    private  final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BUSINESS_YELP_ID + " TEXT NOT NULL, " + USER_NAME + " TEXT NOT NULL," +
            USER_RATING + " TEXT NOT NULL, " + USER_TEXT + " NOT NULL, " + USER_DATE + " NOT NULL );";

    public LocalReviewHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
