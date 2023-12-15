package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.mystylistmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BodyTypeActivity extends AppCompatActivity {

    private ImageView backImageView;

    private CardView cardAboutBodyType, cardBodyTypeMeasurement;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the status bar and the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar().hide();

        setContentView(R.layout.activity_body_type);

        cardAboutBodyType = findViewById(R.id.cardAboutBodyType);
        cardBodyTypeMeasurement = findViewById(R.id.cardBodyTypeMeasurement);
        backImageView = findViewById(R.id.image_back);

        this.cardAboutBodyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(BodyTypeActivity.this, BodyTypeAboutActivity.class);
            }
        });
        this.cardBodyTypeMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(BodyTypeActivity.this, BodyMeasurementsActivity.class);
            }
        });
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BodyTypeActivity.this, DNAActivity.class);
                startActivity(intent);
            }
        });

    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

}