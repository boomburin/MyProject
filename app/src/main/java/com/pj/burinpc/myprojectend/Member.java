package com.pj.burinpc.myprojectend;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public String[] searchUserName(String strUserName) {
        try {
            String[] strMyResult = null;
            Cursor objCursor = readSqLiteDatabase.query(MEMBER_TABLE,
                    new String[]{COLUMN_MEMBER_ID, COLUMN_MEMBER_USERNAME, COLUMN_MEMBER_PASS,
                            COLUMN_MEMBER_NAME, COLUMN_MEMBER_TEL}, COLUMN_MEMBER_USERNAME + "=?",
                    new String[]{String.valueOf(strUserName)},
                    null, null, null, null);
            if (objCursor != null) {
                if (objCursor.moveToFirst()) {
                    strMyResult = new String[objCursor.getColumnCount()];
                    strMyResult[0] = objCursor.getString(0);
                    strMyResult[1] = objCursor.getString(1);
                    strMyResult[2] = objCursor.getString(2);
                    strMyResult[3] = objCursor.getString(3);
                    strMyResult[4] = objCursor.getString(4);
                } // if 2
            } // if 1
            objCursor.close();
            return strMyResult;
        }catch (Exception e) {
            return null;
        }
        //return new String[0];
    } // searchUser

    public long addMember(String strUserName, String strPass, String strName, String strTel){
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_MEMBER_USERNAME, strUserName);
        objContentValues.put(COLUMN_MEMBER_PASS, strPass);
        objContentValues.put(COLUMN_MEMBER_NAME, strName);
        objContentValues.put(COLUMN_MEMBER_TEL, strTel);
        return readSqLiteDatabase.insert(MEMBER_TABLE, null, objContentValues);
    } // addMember

} // Main Class
