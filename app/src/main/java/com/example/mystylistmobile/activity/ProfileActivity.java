package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.ForgotPasswordRequest;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.UserResponseDTO;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.AuthService;
import com.example.mystylistmobile.service.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RetrofitService retrofitService;
    private LoadingAlert loadingAlert;

    private TextView titleUsername, profileName, profileEmail;

    private ImageView resetPasswordImage, logOutImage;

    private SessionManager sessionManager;


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

        setContentView(R.layout.activity_profile);
        retrofitService = new RetrofitService();
        sessionManager = new SessionManager(this);
        loadingAlert = new LoadingAlert(ProfileActivity.this);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
        titleUsername = findViewById(R.id.titleUsername);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        resetPasswordImage = findViewById(R.id.resetPasswordImage);
        logOutImage = findViewById(R.id.logOutImage);
        resetPasswordImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();

            }
        });
        logOutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Logout",Toast.LENGTH_SHORT).show();
                sessionManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
        loadInformationOfUser();
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
        handleBottomNavigation();
    }

    public void resetPassword(){
        loadingAlert.startAlertDialog();
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail((String) profileEmail.getText());
        AuthService authService = retrofitService.createService(AuthService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        authService.forgotPassword(forgotPasswordRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                    loadingAlert.closeDialog();
                    startActivity(new Intent(ProfileActivity.this, ResetPasswordActivity.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
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
                    titleUsername.setText(userResponseDTO.getName());
                    profileName.setText(userResponseDTO.getName());
                    profileEmail.setText(userResponseDTO.getEmail());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Throwable t) {

            }
        });

    }
    public void handleBottomNavigation(){
        this.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_dna:
                    startActivity(new Intent(getApplicationContext(), DNAActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
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
                    return true;
            }
            return false;
        });
    }
}