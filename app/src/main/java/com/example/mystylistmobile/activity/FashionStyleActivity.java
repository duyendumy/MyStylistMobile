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

public class FashionStyleActivity extends AppCompatActivity {

    private CardView cardAboutStyleType, cardStyleTypeQuiz;

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

        setContentView(R.layout.activity_fashion_style);
        cardAboutStyleType = findViewById(R.id.cardAboutStyleType);
        cardStyleTypeQuiz = findViewById(R.id.cardStyleTypeQuiz);
        backImageView = findViewById(R.id.image_back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FashionStyleActivity.this, DNAActivity.class);
                startActivity(intent);
            }
        });

        this.cardAboutStyleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(FashionStyleActivity.this, FashionStyleAboutActivity.class);
            }
        });
        this.cardStyleTypeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(FashionStyleActivity.this, FashionStyleQuizActivity.class);
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