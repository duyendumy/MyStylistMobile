package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.ForgotPasswordRequest;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private EditText inputEmail;

    private Button submitBtn;

    private LoadingAlert loadingAlert;

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
        setContentView(R.layout.activity_forgot_password);
        retrofitService = new RetrofitService();
        loadingAlert = new LoadingAlert(ForgotPasswordActivity.this);
        inputEmail = findViewById(R.id.inputEmail);
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }

    public void resetPassword(){
        String email = String.valueOf(inputEmail.getText());
        if(email.isEmpty()){
            inputEmail.requestFocus();
            inputEmail.setError("Please enter your email");
            return;
        }
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(email);
        AuthService authService = retrofitService.createService(AuthService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        loadingAlert.startAlertDialog();
        authService.forgotPassword(forgotPasswordRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadingAlert.closeDialog();
                startActivity(new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}