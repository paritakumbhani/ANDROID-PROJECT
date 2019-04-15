package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DictionaryDatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "dictionaryDatabase";
    final static int VERSION = 1;
    final static String TABLE_NAME = "SavedDefinitions";//table name
    final static String COL_TITLE = "TITLE";//title
    final static String COL_DEFINITION = "DEFINITION";// definition

    public DictionaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("Database onCreate:", "creating database");

        db.execSQL("CREATE TABLE " + TABLE_NAME + "( id INTEGER PRIMARY KEY AUTOINCREMENT, TITLE text, DEFINITION text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("Database upgrade:", " Old version:" + oldVersion + " newVersion:"+newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database downgrade", " Old version:" + oldVersion + " newVersion:"+newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public SQLiteDatabase getWriteableDatabase()
    {
        return super.getWritableDatabase();
    }
}
