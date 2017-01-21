package com.pj.burinpc.myprojectend;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyAlertDialog {
    AlertDialog.Builder objAlert;

    public void errorDialog(Context context, String strTitle, String strMessage) {
        objAlert = new AlertDialog.Builder(context);
        objAlert.setIcon(R.drawable.question);
        objAlert.setTitle(strTitle);
        objAlert.setMessage(strMessage);
        objAlert.setCancelable(false);
                objAlert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objAlert.show();

    } // errorDialog

} // Main Class
