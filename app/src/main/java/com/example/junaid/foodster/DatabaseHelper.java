package com.example.junaid.foodster;;

// sources
// http://stacktips.com/tutorials/android/android-sqlite-database-tutorial

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_FIRSTNAME = "fname";
    public static final String COLUMN_LASTNAME = "lname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_UNAME = "username";
    public static final String COLUMN_PASS = "password";
    public static final String COLUMN_ID = "ID";


    // database version
    static final int DB_VERSION = 1;

    public static boolean doesDatabaseExist(Context passedContext) {
        File dbFile = passedContext.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    public static boolean doesTableExist(DatabaseController db) {
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
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRSTNAME + " TEXT NOT NULL, " + COLUMN_LASTNAME + " TEXT NOT NULL, " 
            + COLUMN_EMAIL + " TEXT NOT NULL, " + COLUMN_UNAME + " TEXT NOT NULL, " + COLUMN_PASS + " TEXT NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
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

    public void createTheDB(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
}
