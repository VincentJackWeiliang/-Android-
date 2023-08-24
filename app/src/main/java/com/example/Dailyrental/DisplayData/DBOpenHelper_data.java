package com.example.Dailyrental.DisplayData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper_data extends SQLiteOpenHelper {
    public DBOpenHelper_data(Context context) {
        super(context, "data.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE information(_id INTEGER PRIMARY KEY AUTOINCREMENT,business_name VARCHAR(20),name VARCHAR(20),number VARCHAR(20),price VARCHAR(20),service VARCHAR(100),address VARCHAR(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
