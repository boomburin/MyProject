package com.pj.burinpc.myprojectend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteOpen extends SQLiteOpenHelper {
    // Explicit
    private static final String DATABASE_NAME = "fourchokco_boom.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_MEMBER_TABLE = "create table member " +
            "(MemberID integer primary key , MemberUserName text, MemberPass text, MemberName text, MemberTel text, MemberStatus);";
    private static final String CREATE_STADIUM_TABLE = "create table stadium " +
            "(StadiumID integer primary key, StadiumName text, StadiumPrice text);";
    private static final String CREATE_EMPBOOK_TABLE = "create table emp_book " +
            "(EB_ID integer primary key, EB_Username text, EB_Pass text, EB_Name text, EB_Status);";
    private static final String CREATE_BOOKING_TABLE = "create table booking " +
            "(BookID integer primary key, EB_ID text, MemberID text, BookName text, BookDate text);";
    private static final String CREATE_BOOKINGDETAIL_TABLE = "create table booking_detail " +
            "(BD_ID integer primary key, BookID text, StadiumID text, StartTime text, OverTime text, Total text);";

    public SQLiteOpen(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    } // Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MEMBER_TABLE);
        db.execSQL(CREATE_STADIUM_TABLE);
        db.execSQL(CREATE_EMPBOOK_TABLE);
        db.execSQL(CREATE_BOOKING_TABLE);
        db.execSQL(CREATE_BOOKINGDETAIL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
} // Main Class
