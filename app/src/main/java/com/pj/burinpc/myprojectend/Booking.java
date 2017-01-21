package com.pj.burinpc.myprojectend;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class Booking {
    // Explicit
    private SQLiteOpen objSQLiteOpen;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String BOOKING_TABLE = "booking";
    public static final String COLUMN_BOOKING_ID = "BookID";
    public static final String COLUMN_BOOKING_EB_ID = "EB_ID";
    public static final String COLUMN_BOOKING_MEMBER_ID = "MemberID";
    public static final String COLUMN_BOOKING_NAME = "BookName";
    public static final String COLUMN_BOOKING_DATE = "BookDate";

    public Booking(Context context) {
        objSQLiteOpen = new SQLiteOpen(context);
        writeSqLiteDatabase = objSQLiteOpen.getWritableDatabase();
        readSqLiteDatabase = objSQLiteOpen.getReadableDatabase();

    } // Constructor

    public long addBooking (String strEmpBookID, String strMemberID, String strBookName, String strBookDate){
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_BOOKING_EB_ID, strEmpBookID);
        objContentValues.put(COLUMN_BOOKING_MEMBER_ID, strMemberID);
        objContentValues.put(COLUMN_BOOKING_NAME, strBookName);
        objContentValues.put(COLUMN_BOOKING_DATE, strBookDate);
        return readSqLiteDatabase.insert(BOOKING_TABLE, null, objContentValues);

    } // addBooking
} // Main Class
