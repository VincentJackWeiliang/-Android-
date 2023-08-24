package com.example.Dailyrental.Business;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper_Saledetalis extends SQLiteOpenHelper {
    public DBOpenHelper_Saledetalis(Context context) {
        super(context, "saledetalis.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE information(_id INTEGER PRIMARY KEY AUTOINCREMENT,business_name VARCHAR(20),customer_name VARCHAR(20),order_name VARCHAR(20),price VARCHAR(20),service VARCHAR(100),address VARCHAR(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}