package com.example.junaid.foodster;

// sources
// http://stacktips.com/tutorials/android/android-sqlite-database-tutorial

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class UserTypeHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "USERTYPE";

    // Table columns
    public static final String USERTYPE_ID = "UserType_id";
    public static final String USERTYPE_NAME = "UserType_name";
    public static final String USER_TYPE = "User_Type";
    // Database Information
    static final String DB_NAME = "users.db";

    // database version
    static final int DB_VERSION = 1;

    public  boolean doesDatabaseExist(Context passedContext) {
        File dbFile = passedContext.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    public  boolean doesTableExist(UserTypeController db) {
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
    private  final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + USERTYPE_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERTYPE_NAME + " TEXT NOT NULL, " + USER_TYPE + " TEXT NOT NULL);";

    public UserTypeHelper(Context context) {
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
