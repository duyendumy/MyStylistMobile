package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.mystylistmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManagementSeasonalColorQuestion extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private CardView cardUndertoneQuestion, cardContrastQuestion;

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
        setContentView(R.layout.activity_management_seasonal_color_question);

        cardUndertoneQuestion = findViewById(R.id.cardUndertoneQuestion);
        cardContrastQuestion = findViewById(R.id.cardContrastQuestion);

        cardUndertoneQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UndertoneQuestionActivity.class));
            }
        });

        cardContrastQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ContrastQuestionActivity.class));
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_seasonal_color_question);
        handleBottomNavigation();
    }

    public void handleBottomNavigation(){
        this.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_user:
                    startActivity(new Intent(getApplicationContext(), UserActivity.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    finish();
                    return true;
                case R.id.bottom_category:
                    startActivity(new Intent(getApplicationContext(), ManagementCategoryActivity.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    finish();
                    return true;
                case R.id.bottom_item:
                    startActivity(new Intent(getApplicationContext(), ManagementItemActivity.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    finish();
                    return true;
                case R.id.bottom_seasonal_color_question:
                    return true;
                case R.id.bottom_style_type_question:
                    startActivity(new Intent(getApplicationContext(), ManagementStyleTypeQuestion.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }
}