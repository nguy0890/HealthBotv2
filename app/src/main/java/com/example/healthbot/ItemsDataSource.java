package com.example.healthbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ItemsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteProfile dbOpenHelper;
    private String[] columns = {MySQLiteProfile.User_ID,
            MySQLiteProfile.USER_NAME, MySQLiteProfile.USER_GENDER, MySQLiteProfile.USER_BIRTH};

    private static final String TAG = "myItemDB";

    // call to database constructor
    ItemsDataSource(Context context) {
        dbOpenHelper = new MySQLiteProfile(context);
    }

    public void open() throws SQLException {
        //database = dbHelper.getWritableDatabase();
        database = dbOpenHelper.getReadableDatabase();
    }

    public void close() {
        dbOpenHelper.close();
    }


    public User createItem(User item) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteProfile.USER_NAME, item.getUser());
        values.put(MySQLiteProfile.USER_GENDER, item.getGender());
        values.put(MySQLiteProfile.USER_BIRTH, item.getBirth());
        long insertId = database.insert(MySQLiteProfile.TABLE_Of_Users, null,
                values);

        Cursor cursor = database.query(MySQLiteProfile.TABLE_Of_Users,
                columns, MySQLiteProfile.User_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        User newItem = cursorToItem(cursor);

        // Log the item stored
        Log.d(TAG, "item = " + cursorToItem(cursor).toString()
                + " insert ID = " + insertId);
        cursor.close();
        return newItem;
    }

    public void deleteItem(User item) {
        long id = item.getId();
        Log.d(TAG, "delete item = " + id);
        System.out.println("Item deleted with id: " + id);

        database.delete(MySQLiteProfile.TABLE_Of_Users, MySQLiteProfile.User_ID
                + " = " + id, null);

    }

    public void deleteAllItems() {
        System.out.println("Item deleted all");
        Log.d(TAG, "delete all = ");

        database.delete(MySQLiteProfile.TABLE_Of_Users, null, null);
    }

    public List <User> getAllItem() {
        List <User> items = new ArrayList <>();
        Cursor cursor = database.query(MySQLiteProfile.TABLE_Of_Users,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User item = cursorToItem(cursor);
            Log.d(TAG, "get item = " + cursorToItem(cursor).toString());
            items.add(item);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return items;
    }


    public long count() {
        long count = DatabaseUtils.queryNumEntries(database, MySQLiteProfile.TABLE_Of_Users);
        return count;
    }


    public User updateItem(String item, int id, int gender, int birth) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteProfile.User_ID, id + "");
        values.put(MySQLiteProfile.USER_NAME, item);
        values.put(MySQLiteProfile.USER_GENDER, gender);
        values.put(MySQLiteProfile.USER_BIRTH, birth);

        database.update(MySQLiteProfile.TABLE_Of_Users, values, MySQLiteProfile.User_ID
                + " = " + id, null);
        User it = new User();
        it.setUser(item);
        it.setId(id);
        it.setGender(gender);
        it.setBirth(birth);
        return it;
    }

    private User cursorToItem(Cursor cursor) {
        User item = new User();
        item.setId(cursor.getLong(0));
        item.setUser(cursor.getString(1));
        item.setGender(cursor.getInt(2)); //added
        item.setBirth(cursor.getInt(3));
        return item;
    }
}