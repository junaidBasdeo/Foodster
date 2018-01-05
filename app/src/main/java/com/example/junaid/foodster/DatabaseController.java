package com.example.junaid.foodster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DatabaseController {

    private DatabaseHelper DatabaseHelper;
    private Context ourcontext;
    private SQLiteDatabase database;
    // get the DB
    public DatabaseController(Context c) {
        ourcontext = c;
    }

    public DatabaseController open() throws SQLException {
        DatabaseHelper = new DatabaseHelper(ourcontext);
        database = DatabaseHelper.getWritableDatabase();
        return this;

    }

    public void close() {
        DatabaseHelper.close();
    }

    public void insert(String firstname, String lastname, String email, String username, String password) {
        try {
            ContentValues values = new ContentValues();
            values.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_FIRSTNAME, firstname);
            values.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_LASTNAME, lastname);
            values.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_EMAIL, email);
            values.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_UNAME, username);
            values.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_PASS, password);
            // need to insert the DB
            database.insert(com.example.junaid.foodster.DatabaseHelper.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("Info",  "Exception Generated in method adduser insert " + e.getMessage() + " Exception Type : " + e.getClass().toString());

        }   }

    public void insertAdmin(String username, String password) {
        try {
            ContentValues values = new ContentValues();
            values.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_UNAME, username);
            values.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_PASS, password);
            // need to insert the DB
            database.insert(com.example.junaid.foodster.DatabaseHelper.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("Info",  "Exception Generated in method adduser insertAdmin " + e.getMessage() + " Exception Type : " + e.getClass().toString());

        }
    }

    public Cursor query() {
        String[] columns = new String[] {com.example.junaid.foodster.DatabaseHelper.COLUMN_UNAME, com.example.junaid.foodster.DatabaseHelper.COLUMN_PASS};
        Cursor cursor = database.query(com.example.junaid.foodster.DatabaseHelper.TABLE_NAME, columns, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public Cursor rawQuery(String sql) {
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public int update(long username, String password, long ID) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_UNAME, username);
        contentValues.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_PASS, password);
        int i = database.update(com.example.junaid.foodster.DatabaseHelper.TABLE_NAME, contentValues,
                com.example.junaid.foodster.DatabaseHelper.COLUMN_ID + " = " + ID, null);
        return i;
    }

    public int update(long user_id, String name, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_UNAME, name);
        contentValues.put(com.example.junaid.foodster.DatabaseHelper.COLUMN_PASS, password);
        int i = database.update(com.example.junaid.foodster.DatabaseHelper.TABLE_NAME, contentValues,
                com.example.junaid.foodster.DatabaseHelper.COLUMN_ID + " = " + user_id, null);
        return i;
    }
    public void delete(long ID) {
        database.delete(com.example.junaid.foodster.DatabaseHelper.TABLE_NAME, com.example.junaid.foodster.DatabaseHelper.COLUMN_ID + "=" + ID, null);
    }
    public void startOver(DatabaseController db) {
        db.rawQuery("DROP TABLE IF EXISTS " + com.example.junaid.foodster.DatabaseHelper.TABLE_NAME);
    }


}