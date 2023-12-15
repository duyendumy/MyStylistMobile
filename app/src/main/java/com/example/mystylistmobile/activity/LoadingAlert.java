package com.example.mystylistmobile.activity;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.mystylistmobile.R;

public class LoadingAlert {
    private Activity activity;
    private AlertDialog dialog;

    public LoadingAlert(Activity activity) {
        this.activity = activity;
    }

    public void startAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_layout, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }

    public void closeDialog(){
        dialog.dismiss();
    }
}
