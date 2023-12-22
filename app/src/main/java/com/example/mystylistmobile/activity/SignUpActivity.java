package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.auth.LoginRequest;
import com.example.mystylistmobile.dto.auth.UserCreateDTO;
import com.example.mystylistmobile.dto.response.RegisterV2ResponseDTO;
import com.example.mystylistmobile.dto.response.TokenDTO;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.AuthService;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private RetrofitService retrofitService;
    private EditText userNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpBtn;
    private TextView logInTextView;

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

        setContentView(R.layout.activity_sign_up);
        userNameEditText = findViewById(R.id.userNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpBtn = findViewById(R.id.signUpBtn);
        logInTextView = findViewById(R.id.logInTextView);
        signUp();
        logIn();
    }

    public void signUp(){
        signUpBtn.setOnClickListener(view -> {
            String name = String.valueOf(userNameEditText.getText());
            String email = String.valueOf(emailEditText.getText());
            String password = String.valueOf(passwordEditText.getText());
            String confirmPassword = String.valueOf(confirmPasswordEditText.getText());

            if(name.isEmpty()){
                userNameEditText.requestFocus();
                userNameEditText.setError("Please enter your name");
                return;
            }
            if(email.isEmpty()){
                emailEditText.requestFocus();
                emailEditText.setError("Please enter your email");
                return;
            }
            if(password.isEmpty()){
                passwordEditText.requestFocus();
                passwordEditText.setError("Please enter your password");
                return;
            }
            if(confirmPassword.isEmpty()){
                confirmPasswordEditText.requestFocus();
                confirmPasswordEditText.setError("Please enter your confirm password");
                return;
            }
            if(!confirmPassword.equals(password)){
                confirmPasswordEditText.requestFocus();
                confirmPasswordEditText.setError("Password did not match");
                return;
            }
            retrofitService = new RetrofitService();
            sessionManager = new SessionManager(this);
            UserCreateDTO userCreateDTO = new UserCreateDTO(email, password, name);

            AuthService authService = retrofitService.createService(AuthService.class);
            authService.register(userCreateDTO).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(SignUpActivity.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Sign up failed!", Toast.LENGTH_SHORT).show();
                    Logger.getLogger(SignUpActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
                }
            });
        });
    }

    public void logIn(){
        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}


