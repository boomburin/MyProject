package com.pj.burinpc.myprojectend;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class EmpBook {
    // Explicit
    private SQLiteOpen objSQLiteOpen;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String EMPBOOK_TABLE = "emp_book";
    public static final String COLUMN_EMPBOOK_ID = "EB_ID";
    public static final String COLUMN_EMPBOOK_EBUSER = "EB_Username";
    public static final String COLUMN_EMPBOOK_EBPASS = "EB_Pass";
    public static final String COLUMN_EMPBOOK_EBNAME = "EB_Name";
    public static final String COLUMN_EMPBOOK_STATUS = "EB_Status";

    public EmpBook(Context context) {
        objSQLiteOpen = new SQLiteOpen(context);
        writeSqLiteDatabase = objSQLiteOpen.getWritableDatabase();
        readSqLiteDatabase = objSQLiteOpen.getReadableDatabase();

    } // Constructor

    public long addEmpBook(String strEB_ID, String strEBUsername, String strEBPass, String strEBName, String strEBStatus){
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_EMPBOOK_ID, strEB_ID);
        objContentValues.put(COLUMN_EMPBOOK_EBUSER, strEBUsername);
        objContentValues.put(COLUMN_EMPBOOK_EBPASS, strEBPass);
        objContentValues.put(COLUMN_EMPBOOK_EBNAME, strEBName);
        objContentValues.put(COLUMN_EMPBOOK_STATUS, strEBStatus);
        return readSqLiteDatabase.insert(EMPBOOK_TABLE, null, objContentValues);

    } // addEmpBook

} // Main Class
