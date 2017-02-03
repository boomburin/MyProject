package com.pj.burinpc.myprojectend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

    private EditText userEditText, passEditText;
    private String strUserChoose, strPassChoose;
    private String strPasswordTrue, strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initial widget
        initialWidget();

        // Connected SQLite
        connectedSQLite();

        // Delete All Data
        deleteAllData();

        // Synchronize JSON to SQLite
        synJSON();

    } // On Create

    private void initialWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passEditText = (EditText) findViewById(R.id.editText2);

    } // initialWidget

    public void clickLogin(View view) {
        strUserChoose = userEditText.getText().toString().trim();
        strPassChoose = passEditText.getText().toString().trim();


        // check null
        if (strUserChoose.equals("") || strPassChoose.equals("")) {
            // Alert error
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.errorDialog(MainActivity.this, "มีช่องว่างเปล่า", "กรุณากรอกให้ครบ ทุกช่อง");
        }else {
            chekUserName();
        }
    } // click login

    private void chekUserName() {
        try {
            String[] strMyResult = objMember.searchUserName(strUserChoose);
            strPasswordTrue = strMyResult[2];
            strName = strMyResult[3];

            Log.d("masterPJ", "ยินดีต้อนรับ :  " + strName);

            checkPass();

        } catch (Exception e) {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.errorDialog(MainActivity.this, "  ไม่มีชื่อผู้ใช้", "ไม่มี " + strUserChoose + " ในระบบของเรา");
        }

    } // Check User

    private void checkPass() {
        if (strPassChoose.equals(strPasswordTrue)) {
            // Intent to BookingActivity
            welcomeMember();

        } else {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.errorDialog(MainActivity.this, "รหัสผ่านผิดพลาด", "กรุณากรอกรหัสผ่านใหม่อีกครั้ง");
        }

    } // Check Password

    private void welcomeMember() {
        AlertDialog.Builder objAlert = new AlertDialog.Builder(this);
        //objAlert.setIcon(R.drawable.icon_football);
        objAlert.setTitle("Samutprakan United");
        objAlert.setMessage("ยินดีต้อนรับ " + strName + "\n" + " สู่ระบบของเรา");
        objAlert.setCancelable(false);
        objAlert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent objIntent = new Intent(MainActivity.this, ShowStadium.class);
                startActivity(objIntent);
                finish();
            }
        });
        objAlert.show();

    } // Welcome Member

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
                            String strMemberID = jsonObject.getString("MemberID");
                            String strUserName = jsonObject.getString("MemberUserName");
                            String strPass = jsonObject.getString("MemberPass");
                            String strName = jsonObject.getString("MemberName");
                            String strTel =  jsonObject.getString("MemberTel");
                            String strStatus = jsonObject.getString("MemberStatus");
                            objMember.addMember(strMemberID, strUserName, strPass, strName, strTel, strStatus);
                            break;
                        case 1:
                            // Update stadium table
                            String strStadiumID = jsonObject.getString("StadiumID");
                            String strStadiumName = jsonObject.getString("StadiumName");
                            String strStadiumPrice = jsonObject.getString("StadiumPrice");
                            objStadium.addStadium(strStadiumID, strStadiumName, strStadiumPrice);
                            break;
                        case 2:
                            // Update emp_book table
                            String strEB_ID = jsonObject.getString("EB_ID");
                            String strEmpUser = jsonObject.getString("EB_Username");
                            String strEmpPass = jsonObject.getString("EB_Pass");
                            String strEmpName = jsonObject.getString("EB_Name");
                            String strEBStatus = jsonObject.getString("EB_Status");
                            objEmpBook.addEmpBook(strEB_ID, strEmpUser, strEmpPass, strEmpName, strEBStatus);
                            break;
                        case 3:
                            // update booking table
                            String strBookID = jsonObject.getString("BookID");
                            String strEmpID = jsonObject.getString("EB_ID");
                            String strmemberID = jsonObject.getString("MemberID");
                            String strBookName = jsonObject.getString("BookName");
                            String strBookDate = jsonObject.getString("BookDate");
                            objBooking.addBooking(strBookID, strEmpID, strmemberID, strBookName, strBookDate);
                            break;
                        default:
                            // Update booking_detail table
                            String strBD_ID = jsonObject.getString("BD_ID");
                            String strbookID = jsonObject.getString("BookID");
                            String strstadiumID = jsonObject.getString("StadiumID");
                            String strStartTime = jsonObject.getString("StartTime");
                            String strOverTine = jsonObject.getString("OverTime");
                            String strTotal = jsonObject.getString("Total");
                            objBookingDetail.addBookingDetail(strBD_ID, strbookID, strstadiumID, strStartTime,
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

    public void clickRegis(View view){
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        finish();
    } // ClickRegister

    private void deleteAllData() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("fourchokco_boom.db", MODE_APPEND, null);
        objSqLiteDatabase.delete("member", null, null);
        objSqLiteDatabase.delete("stadium", null, null);
        objSqLiteDatabase.delete("emp_book", null, null);
        objSqLiteDatabase.delete("booking", null, null);
        objSqLiteDatabase.delete("booking_detail", null, null);

    } // Delete all data
}
