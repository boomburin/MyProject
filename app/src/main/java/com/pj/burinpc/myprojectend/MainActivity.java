package com.pj.burinpc.myprojectend;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
            HttpPost objHttpPost;

            // 1. Create Inputstream
            try {
                HttpClient objHttpClient = new DefaultHttpClient();
                switch (inTimes) {
                    case 0:
                        objHttpPost = new HttpPost(strMemberURL);
                        break;
                    case 1:
                        objHttpPost = new HttpPost(strStadiumURL);
                        break;
                    case 2:
                        objHttpPost = new HttpPost(strEmpURL);
                        break;
                    case 3:
                        objHttpPost = new HttpPost(strBookingURL);
                        break;
                    default:
                        objHttpPost = new HttpPost(strBookingDetailURL);
                        break;
                }
                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();

            }catch (Exception e) {
                Log.d("masterPJ", "InputStream ==>" + e.toString());
            }

            // 2. Create strJSON
            try {
                BufferedReader objBufferedReader = new BufferedReader
                        (new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();
                String strLine = null;
                while ((strLine = objBufferedReader.readLine()) != null) {
                    objStringBuilder.append(strLine);
                }
                objInputStream.close();
                strJson = objStringBuilder.toString();

            }catch (Exception e){
                Log.d("masterPJ", "strJson ==> " + e.toString());
            }

            // 3. Update to SQLite
            try {
                JSONArray objJsonArray = new JSONArray(strJson);
                for (int i = 0; i<objJsonArray.length(); i++) {
                    JSONObject jsonObject = objJsonArray.getJSONObject(i);
                    switch (inTimes) {
                        case 0:
                            // Update member table
                            String strUserName = jsonObject.getString("MemberUserName");
                            String strPass = jsonObject.getString("MemberPass");
                            String strName = jsonObject.getString("MemberName");
                            String strTel =  jsonObject.getString("MemberTel");
                            objMember.addMember(strUserName, strPass, strName, strTel);
                            break;
                        case 1:
                            // Update stadium table
                            String strStadiumName = jsonObject.getString("StadiumName");
                            String strStadiumPrice = jsonObject.getString("StadiumPrice");
                            objStadium.addStadium(strStadiumName, strStadiumPrice);
                            break;
                        case 2:
                            // Update emp_book table
                            String strEmpUser = jsonObject.getString("EB_Username");
                            String strEmpPass = jsonObject.getString("EB_Pass");
                            String strEmpName = jsonObject.getString("EB_Name");
                            objEmpBook.addEmpBook(strEmpUser, strEmpPass, strEmpName);
                            break;
                        case 3:
                            // update booking table
                            String strEmpID = jsonObject.getString("EB_ID");
                            String strMemberID = jsonObject.getString("MemberID");
                            String strBookName = jsonObject.getString("BookName");
                            String strBookDate = jsonObject.getString("BookDate");
                            objBooking.addBooking(strEmpID, strMemberID, strBookName, strBookDate);
                            break;
                        default:
                            // Update booking_detail table
                            String strBookID = jsonObject.getString("BookID");
                            String strStadiumID = jsonObject.getString("StadiumID");
                            String strStartTime = jsonObject.getString("StartTime");
                            String strOverTine = jsonObject.getString("OverTime");
                            String strTotal = jsonObject.getString("Total");
                            objBookingDetail.addBookingDetail(strBookID, strStadiumID, strStartTime,
                                    strOverTine, strTotal);
                            break;
                    }
                }
            }catch (Exception e) {
                Log.d("masterPJ", "Update SQLite ==> " + e.toString());
            }

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
