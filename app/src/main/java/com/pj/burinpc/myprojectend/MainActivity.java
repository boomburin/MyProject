package com.pj.burinpc.myprojectend;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    // Exolicit
    private Member objMember;
    private Stadium objStadium;
    private EmpBook objEmpBook;
    private Booking objBooking;
    private BookingDetail objBookingDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connected SQLite
        connectedSQLite();

        // Synchronize JSON to SQLite
        synJSON();

    } // On Create

    private void synJSON() {
        StrictMode.ThreadPolicy Mypolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(Mypolicy);

        // Loop 3 Times
        int inTimes = 0;
        while (inTimes <= 3) {
            // Varible & Constant
            InputStream objInputStream = null;
            String strJson = null;
            String strMemberURL = "http://www.fourchokcodding.com/boom/get/get_member.php";
            String strStadiumURL = "http://www.fourchokcodding.com/boom/get/get_stadium.php";
            String strEmpURL = "http://www.fourchokcodding.com/boom/get/get_emp_book.php";
            String strBookingURL = "http://www.fourchokcodding.com/boom/get/get_booking.php";
            String strBookingDetailURL = "http://www.fourchokcodding.com/boom/get/get_booking_detail.php";

            // 1. Create Inputstream

            // 2. Create strJSON

            // 3. Update to SQLite

            // Increase inTimes
            inTimes += 1;
        } // While

    } // Synchronize JSON to SQLite

    private void connectedSQLite() {
        objMember = new Member(this);
        objStadium = new Stadium(this);
        objEmpBook = new EmpBook(this);
        objBooking = new Booking(this);
        objBookingDetail = new BookingDetail(this);

    } // Connected SQLite
}
