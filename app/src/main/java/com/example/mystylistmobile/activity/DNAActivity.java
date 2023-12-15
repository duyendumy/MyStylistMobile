package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.mystylistmobile.R;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DNAActivity extends AppCompatActivity {

    private TextView userSeasonalColor, userStyleType, userBodyShape;

    private SessionManager sessionManager;

    private RetrofitService retrofitService;

    private CardView recCardSeasonalColor, recCardStyleType, recCardBodyType;

    private BottomNavigationView bottomNavigationView;

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

        setContentView(R.layout.activity_dna);
        userSeasonalColor = findViewById(R.id.userSeasonalColor);
        userStyleType = findViewById(R.id.userStyleType);
        userBodyShape = findViewById(R.id.userBodyShape);
        recCardSeasonalColor = findViewById(R.id.recCardSeasonalColor);
        recCardStyleType = findViewById(R.id.recCardStyleType);
        recCardBodyType = findViewById(R.id.recCardBodyType);

        String strSeasonalColor = sessionManager.getInstance(this).getSeasonalColorName();
        String strStyleType = sessionManager.getInstance(this).getStyleTypeName();
        String strBodyShape = sessionManager.getInstance(this).getBodyShapeName();

        if(strSeasonalColor.toLowerCase() == "default" || strSeasonalColor.toLowerCase() == ""){
            userSeasonalColor.setText("Discover your seasonal color");
        } else {
            userSeasonalColor.setText(strSeasonalColor);
        }

        if(strStyleType.toLowerCase() == "default" || strStyleType.toLowerCase() == "" ){
            userStyleType.setText("Discover your fashion style");
        } else {
            userStyleType.setText(strStyleType);
        }

        if(strBodyShape.toLowerCase() == "default" || strBodyShape.toLowerCase() == ""){
            userBodyShape.setText("Discover your body shape");
        } else {
            userBodyShape.setText(strBodyShape);
        }

       this.recCardSeasonalColor.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               redirectActivity(DNAActivity.this, SeasonalColorActivity.class);
           }
       });
        this.recCardStyleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DNAActivity.this, FashionStyleActivity.class);
            }
        });
        this.recCardBodyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DNAActivity.this, BodyTypeActivity.class);
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_dna);
        handleBottomNavigation();

    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public void handleBottomNavigation(){
        this.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_dna:
                    return true;
                case R.id.bottom_category:
                    startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_analysis:
                    startActivity(new Intent(getApplicationContext(), AIStylistActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_closet:
                    startActivity(new Intent(getApplicationContext(), ClosetActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }
}