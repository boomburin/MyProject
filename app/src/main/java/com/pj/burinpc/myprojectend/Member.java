package com.pj.burinpc.myprojectend;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Member {
    // Explicit
    private SQLiteOpen objSQLiteOpen;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String MEMBER_TABLE = "member";
    public static final String COLUMN_MEMBER_ID = "MemberID";
    public static final String COLUMN_MEMBER_USERNAME = "MemberUserName";
    public static final String COLUMN_MEMBER_PASS = "MemberPass";
    public static final String COLUMN_MEMBER_NAME = "MemberName";
    public static final String COLUMN_MEMBER_TEL = "MemberTel";

    public Member(Context context) {
        objSQLiteOpen = new SQLiteOpen(context);
        writeSqLiteDatabase = objSQLiteOpen.getWritableDatabase();
        readSqLiteDatabase = objSQLiteOpen.getReadableDatabase();

    } // Constructor

    public long addMember(String strUserName, String strPass, String strName, String strTel){
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_MEMBER_USERNAME, strUserName);
        objContentValues.put(COLUMN_MEMBER_PASS, strPass);
        objContentValues.put(COLUMN_MEMBER_NAME, strName);
        objContentValues.put(COLUMN_MEMBER_TEL, strTel);
        return readSqLiteDatabase.insert(MEMBER_TABLE, null, objContentValues);
    } // addMember

} // Main Class
