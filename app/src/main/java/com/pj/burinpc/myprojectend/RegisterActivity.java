package com.pj.burinpc.myprojectend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    // Explicit
    private Member objMember;

    private EditText UserEditText, PassEditText, ConPassEditText, NameEditText, TelEditText;
    private String stringUser, stringPass, stringConPass, stringName, stringTel, stringStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind Widget
        bindWidget();

    } // OnCreate

    public void clickCancelRegister(View view){
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setMessage("ยกเลิกการสมัครหรือไม่");
        dialog.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent objIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(objIntent);
                finish();
            }
        });

        dialog.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialog.show();
    } // On Back

    private boolean checkUser(){
        try {
            objMember = new Member(this);
            String[] strMyResult = objMember.searchUserName(stringUser);
            Log.d("masterPJ", "Name ==>> " + strMyResult[3]);
            return true;

        } catch (Exception e) {
            return false;

        }
    } // CheckUser

    public void clickRegister(View view) {
        stringUser = UserEditText.getText().toString().trim();
        stringPass = PassEditText.getText().toString().trim();
        stringConPass = ConPassEditText.getText().toString().trim();
        stringName = NameEditText.getText().toString().trim();
        stringTel = TelEditText.getText().toString().trim();
        stringStatus = "2";

        if (stringUser.equals("") || stringPass.equals("") || stringConPass.equals("") ||
                stringName.equals("") || stringTel.equals("")) {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.errorDialog(RegisterActivity.this, "มีช่องว่างเปล่า", "กรุณากรอกให้ครบ ทุกช่อง");

        } else {
            if (checkUser()) {
                MyAlertDialog objMyAlert = new MyAlertDialog();
                objMyAlert.errorDialog(RegisterActivity.this, "ชื่อผู่ใช้นี้มีอยู่ในระบบแล้ว", "กรุณาเปลี่ยนชื่อผู้ใช้ใหม่");

            } else {
                checkConfirmPass();
            }
        }

    } //ClickRegister ยืนยันการสมัคร

    private void checkConfirmPass() {
        if (stringPass.equals(stringConPass)) {
            confirmRegister();

        } else {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.errorDialog(RegisterActivity.this, "ยืนยันรหัสผ่านไม่ถูกต้อง", "กรุณายืนยันรหัสผ่านให้เหมือนกัน");
        }
    } // checkConfirmPass

    private void confirmRegister() {
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setTitle("ตรวจสอบข้อมูลการสมัคร");
        objBuilder.setMessage("ชื่อผู้ใช้ = " + stringUser + "\n" +
                "รหัสผ่าน = " + stringPass + "\n" +
                "ชื่อ = " + stringName + "\n" +
                "เบอร์โทรศัพท์ = " + stringTel + "\n");
        objBuilder.setPositiveButton("ยืนยันการสมัคร", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                upDateMySQL();
                finish();
            }
        });
        objBuilder.show();
    } // confirmRegister

    private void upDateMySQL() {
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);
        try {
            String strURL = "http://www.fourchokcodding.com/boom/add/add_member.php";
            ArrayList<NameValuePair> objNameValuePair = new ArrayList<NameValuePair>();
            objNameValuePair.add(new BasicNameValuePair("isAdd","true"));
            objNameValuePair.add(new BasicNameValuePair(Member.COLUMN_MEMBER_USERNAME, stringUser));
            objNameValuePair.add(new BasicNameValuePair(Member.COLUMN_MEMBER_PASS, stringPass));
            objNameValuePair.add(new BasicNameValuePair(Member.COLUMN_MEMBER_NAME, stringName));
            objNameValuePair.add(new BasicNameValuePair(Member.COLUMN_MEMBER_TEL, stringTel));
            objNameValuePair.add(new BasicNameValuePair(Member.COLUMN_MEMBER_STATUS, stringStatus));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost(strURL);
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePair, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(RegisterActivity.this, "สมัครสมาชิกสำเร็จ", Toast.LENGTH_SHORT).show();
            // โชว์ ข้อความ ว่า บันทึกสำเร็จ แล้วหายไป 3.5วื

        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this,"\n" + "สมัครสมาชิกไม่สำเร็จ", Toast.LENGTH_SHORT).show();

        }
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    } // update to MySQL

    private void bindWidget() {
        UserEditText = (EditText) findViewById(R.id.regisUser);
        PassEditText = (EditText) findViewById(R.id.regisPass);
        ConPassEditText = (EditText) findViewById(R.id.regisConfirmPass);
        NameEditText = (EditText) findViewById(R.id.regisName);
        TelEditText = (EditText) findViewById(R.id.regisTel);
    } // Bind Widget


} // Main Class
