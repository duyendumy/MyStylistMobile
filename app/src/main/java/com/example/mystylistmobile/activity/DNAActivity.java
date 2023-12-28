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
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.UserResponseDTO;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DNAActivity extends AppCompatActivity {

    private TextView userSeasonalColor, userStyleType, userBodyShape;

    private SessionManager sessionManager;

    private RetrofitService retrofitService;

    private CardView recCardSeasonalColor, recCardStyleType, recCardBodyType;

    private BottomNavigationView bottomNavigationView;

    private LoadingAlert loadingAlert;

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
        retrofitService = new RetrofitService();
        setContentView(R.layout.activity_dna);
        userSeasonalColor = findViewById(R.id.userSeasonalColor);
        userStyleType = findViewById(R.id.userStyleType);
        userBodyShape = findViewById(R.id.userBodyShape);
        recCardSeasonalColor = findViewById(R.id.recCardSeasonalColor);
        recCardStyleType = findViewById(R.id.recCardStyleType);
        recCardBodyType = findViewById(R.id.recCardBodyType);
        loadingAlert = new LoadingAlert(DNAActivity.this);



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

    public void loadInformationOfUser(){
        loadingAlert.startAlertDialog();

        UserService userService = retrofitService.createService(UserService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        userService.getUserInformation().enqueue(new Callback<ResponseModel<UserResponseDTO, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Response<ResponseModel<UserResponseDTO, ErrorDTO>> response) {
                if(response != null){
                    loadingAlert.closeDialog();
                    UserResponseDTO userResponseDTO = response.body().getResponse();
                    String strSeasonalColor = userResponseDTO.getSeasonalColorName();
                    String strStyleType = userResponseDTO.getStyleTypeName();
                    String strBodyShape = userResponseDTO.getBodyShapeName();
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

                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Throwable t) {
            }
        });

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
                    startActivity(new Intent(getApplicationContext(), ScrollClosetActivity.class));
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