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
import com.example.mystylistmobile.dto.ResetPasswordRequest;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    private RetrofitService retrofitService;
    private EditText inputPassword, inputConfirmPassword, inputToken;
    private Button resetBtn;

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
        setContentView(R.layout.activity_reset_password);

        loadingAlert = new LoadingAlert(ResetPasswordActivity.this);
        retrofitService = new RetrofitService();
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        inputToken = findViewById(R.id.inputToken);
        resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }

    public void resetPassword(){
        String password = String.valueOf(inputPassword.getText());
        String confirmPassword = String.valueOf(inputConfirmPassword.getText());
        String resetToken = String.valueOf(inputToken.getText());
        if(resetToken.isEmpty()){
            inputToken.requestFocus();
            inputToken.setError("Please enter your token that received");
            return;
        }
        if(password.isEmpty()){
            inputPassword.requestFocus();
            inputPassword.setError("Please enter your new password");
            return;
        }
        if(confirmPassword.isEmpty()){
            inputConfirmPassword.requestFocus();
            inputConfirmPassword.setError("Please enter your confirm password");
            return;
        }
        if(!confirmPassword.equals(password)){
            inputConfirmPassword.requestFocus();
            inputConfirmPassword.setError("Password did not match");
            return;
        }
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setNewPassword(password);
        resetPasswordRequest.setToken(resetToken);
        loadingAlert.startAlertDialog();
        AuthService authService = retrofitService.createService(AuthService.class);
        authService.resetPassword(resetPasswordRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadingAlert.closeDialog();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loadingAlert.closeDialog();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}