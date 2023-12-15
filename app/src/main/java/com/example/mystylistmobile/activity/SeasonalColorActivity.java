package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.mystylistmobile.R;

public class SeasonalColorActivity extends AppCompatActivity {

    private CardView cardAboutSeasonalColor, cardSeasonalColorQuiz;

    private ImageView backImageView;

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

        setContentView(R.layout.activity_seasonal_color);
        cardAboutSeasonalColor = findViewById(R.id.cardAboutSeasonalColor);
        cardSeasonalColorQuiz = findViewById(R.id.cardSeasonalColorQuiz);
        backImageView = findViewById(R.id.image_back);

        this.cardAboutSeasonalColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(SeasonalColorActivity.this, SeasonalColorAboutActivity.class);
            }
        });
        this.cardSeasonalColorQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(SeasonalColorActivity.this, SeasonalColorQuizActivity.class);
            }
        });

        this.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeasonalColorActivity.this, DNAActivity.class);
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