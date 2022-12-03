package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class MySQLiteProfile extends SQLiteOpenHelper {

    public static final String TABLE_Of_Users = "tableOfUsers";
    public static final String User_ID = "_id";
    public static final String USER_NAME = "userName";
    public static final String USER_GENDER = "userGender";
    public static final String USER_BIRTH = "userBirth";
    public static final String PROFILE_DATABASE_NAME = "Profile.db";
    private static final int DATABASE_VERSION = 1;


    // Table/Database creation statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_Of_Users + "(" + User_ID
            + " integer primary key autoincrement, " + USER_NAME
            + " text not null," + USER_GENDER
            + " integer not null," + USER_BIRTH
            + " integer not null);";

    // super constructor call

    MySQLiteProfile(Context context) {
        super(context, PROFILE_DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteProfile.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Of_Users);
        onCreate(db);
    }

    public void onDowngrade (SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(MySQLiteProfile.class.getName(),
                "Downgrading database from version " + newVersion + " to "
                        + oldVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_Of_Users);
        onCreate(database);
    }

    public long count(SQLiteDatabase database) {
        long count = 0;
        database.execSQL("select count(*) " + " from " + "  TABLE_Of_Users");
        return count;
    }
}