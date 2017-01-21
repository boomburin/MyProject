package com.pj.burinpc.myprojectend;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BookingDetail {
    // Explicit
    private SQLiteOpen objSQLiteOpen;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String BOOKING_DETAIL_TABLE = "booking_detail";
    public static final String COLUMN_BD_ID = "BD_ID";
    public static final String COLUMN_BD_BOOK_ID = "BookID";
    public static final String COLUMN_BD_STADIUM_ID = "StadiumID";
    public static final String COLUMN_BD_STARTTIME = "StartTime";
    public static final String COLUMN_BD_OVERTIME = "OverTime";
    public static final String COLUMN_BD_TOTAL = "Total";

    public BookingDetail(Context context) {
        objSQLiteOpen = new SQLiteOpen(context);
        writeSqLiteDatabase = objSQLiteOpen.getWritableDatabase();
        readSqLiteDatabase = objSQLiteOpen.getReadableDatabase();
    } // Constructor

    public long addBookingDetail(String strBookingID, String strStadiumID, String strStartTime, String strOverTime, String strTotal){
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_BD_BOOK_ID, strBookingID);
        objContentValues.put(COLUMN_BD_STADIUM_ID, strStadiumID);
        objContentValues.put(COLUMN_BD_STARTTIME, strStartTime);
        objContentValues.put(COLUMN_BD_OVERTIME, strOverTime);
        objContentValues.put(COLUMN_BD_TOTAL, strTotal);
        return readSqLiteDatabase.insert(BOOKING_DETAIL_TABLE, null, objContentValues);

    } // addBookingDetail
} // Main Class
