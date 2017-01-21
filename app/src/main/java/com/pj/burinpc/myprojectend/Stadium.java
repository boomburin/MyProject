package com.pj.burinpc.myprojectend;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Stadium {
    // Explicit
    private SQLiteOpen objSQLiteOpen;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String STADIUM_TABLE = "stadium";
    public static final String COLUMN_STADIUM_ID = "StadiumID";
    public static final String COLUMN_STADIUM_NAME = "StadiumName";
    public static final String COLUMN_STADIUM_PRICE = "StadiumPrice";

    public Stadium(Context context) {
        objSQLiteOpen = new SQLiteOpen(context);
        writeSqLiteDatabase = objSQLiteOpen.getWritableDatabase();
        readSqLiteDatabase = objSQLiteOpen.getReadableDatabase();

    } // Constructor

    public long addStadium(String strStadiumName, String strStadiumPrice){
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_STADIUM_NAME, strStadiumName);
        objContentValues.put(COLUMN_STADIUM_PRICE, strStadiumPrice);
        return readSqLiteDatabase.insert(STADIUM_TABLE, null, objContentValues);

    } //addStadium
} // Main Class
