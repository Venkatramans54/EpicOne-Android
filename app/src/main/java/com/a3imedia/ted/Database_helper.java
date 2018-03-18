package com.a3imedia.ted;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_helper extends SQLiteOpenHelper {
    public Database_helper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LocationDatabase.DATABASE__User_Reg);
        db.execSQL(LocationDatabase.DATABASE__Carrer_Post);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        onCreate(db);
    }
}
