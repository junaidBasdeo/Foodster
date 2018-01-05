package com.example.junaid.foodster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.junaid.foodster.UserTypeController;

public class UserTypeController {

    private UserTypeHelper UserTypeHelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public UserTypeController(Context c) {
        ourcontext = c;
    }

    public UserTypeController open() throws SQLException {
        UserTypeHelper = new UserTypeHelper(ourcontext);
        database = UserTypeHelper.getWritableDatabase();
        return this;

    }

    public void close() {
        UserTypeHelper.close();
    }

    public void insert(String name, String type) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(com.example.junaid.foodster.UserTypeHelper.USERTYPE_NAME, name);
        contentValue.put(com.example.junaid.foodster.UserTypeHelper.USER_TYPE, type);
        database.insert(com.example.junaid.foodster.UserTypeHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor query() {
        String[] columns = new String[] {com.example.junaid.foodster.UserTypeHelper.USERTYPE_ID, com.example.junaid.foodster.UserTypeHelper.USERTYPE_NAME,
                com.example.junaid.foodster.UserTypeHelper.USER_TYPE};
        Cursor cursor = database.query(com.example.junaid.foodster.UserTypeHelper.TABLE_NAME, columns, null,
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
    public int update(long usertype_id, String name, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.example.junaid.foodster.UserTypeHelper.USERTYPE_NAME, name);
        contentValues.put(com.example.junaid.foodster.UserTypeHelper.USER_TYPE, password);
        int i = database.update(com.example.junaid.foodster.UserTypeHelper.TABLE_NAME, contentValues,
                com.example.junaid.foodster.UserTypeHelper.USERTYPE_ID + " = " + usertype_id, null);
        return i;
    }

    public void delete(long usertype_id) {
        database.delete(com.example.junaid.foodster.UserTypeHelper.TABLE_NAME, com.example.junaid.foodster.UserTypeHelper.USERTYPE_ID + "=" + usertype_id, null);
    }
    public void startOver(UserTypeController db) {
        db.rawQuery("DROP TABLE IF EXISTS usertype");
    }


}