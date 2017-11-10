package com.example.junaid.foodster;;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


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
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FIRSTNAME, firstname);
        values.put(DatabaseHelper.COLUMN_LASTNAME, lastname);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_UNAME, username);
        values.put(DatabaseHelper.COLUMN_PASS, password);
    }

    public Cursor query() {
        String[] columns = new String[] { DatabaseHelper.COLUMN_UNAME, DatabaseHelper.COLUMN_PASS };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null,
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
        contentValues.put(DatabaseHelper.COLUMN_UNAME, username);
        contentValues.put(DatabaseHelper.COLUMN_PASS, password);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues,
                DatabaseHelper.COLUMN_ID + " = " + ID, null);
        return i;
    }

    public void delete(long ID) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID + "=" + ID, null);
    }
    public void startOver(DatabaseController db) {
        db.rawQuery("DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_NAME);
        DatabaseHelper.createTheDB(database);

    }


}