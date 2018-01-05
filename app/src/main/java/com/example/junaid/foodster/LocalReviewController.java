package com.example.junaid.foodster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LocalReviewController {

    private LocalReviewHelper LocalReviewHelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public LocalReviewController(Context c) {
        ourcontext = c;
    }

    public LocalReviewController open() throws SQLException {
        LocalReviewHelper = new LocalReviewHelper(ourcontext);
        database = LocalReviewHelper.getWritableDatabase();
        return this;

    }

    public void close() {
        LocalReviewHelper.close();
    }

    public void insert(String name, String business_yelp_id, String date, String text, String rating) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.example.junaid.foodster.LocalReviewHelper.USER_NAME, name);
        contentValues.put(com.example.junaid.foodster.LocalReviewHelper.BUSINESS_YELP_ID, business_yelp_id);
        contentValues.put(com.example.junaid.foodster.LocalReviewHelper.USER_DATE, date);
        contentValues.put(com.example.junaid.foodster.LocalReviewHelper.USER_TEXT, text);
        contentValues.put(com.example.junaid.foodster.LocalReviewHelper.USER_RATING, rating);
        database.insert(com.example.junaid.foodster.LocalReviewHelper.TABLE_NAME, null, contentValues);
    }

    public Cursor query() {
        String[] columns = new String[] {com.example.junaid.foodster.LocalReviewHelper.USER_NAME, com.example.junaid.foodster.LocalReviewHelper.BUSINESS_YELP_ID,
                com.example.junaid.foodster.LocalReviewHelper.USER_DATE, com.example.junaid.foodster.LocalReviewHelper.USER_TEXT, com.example.junaid.foodster.LocalReviewHelper.USER_RATING};
        Cursor cursor = database.query(com.example.junaid.foodster.LocalReviewHelper.TABLE_NAME, columns, null,
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
    public int update(long id, String name, String business_yelp_id, String date, String text, String rating) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.example.junaid.foodster.LocalReviewHelper.USER_NAME, name);
        contentValues.put(com.example.junaid.foodster.LocalReviewHelper.BUSINESS_YELP_ID, business_yelp_id);
        contentValues.put(com.example.junaid.foodster.LocalReviewHelper.USER_DATE, date);
        contentValues.put(com.example.junaid.foodster.LocalReviewHelper.USER_TEXT, text);
        contentValues.put(com.example.junaid.foodster.LocalReviewHelper.USER_RATING, rating);
        int i = database.update(com.example.junaid.foodster.LocalReviewHelper.TABLE_NAME, contentValues,
                com.example.junaid.foodster.LocalReviewHelper.ID + " = " + id, null);
        return i;
    }

    public void delete(long id) {
        database.delete(com.example.junaid.foodster.LocalReviewHelper.TABLE_NAME, com.example.junaid.foodster.LocalReviewHelper.ID + "=" + id, null);
    }
    public void startOver(LocalReviewController db) {
        db.rawQuery("DROP TABLE IF EXISTS LOCALREVIEWS");
    }


}