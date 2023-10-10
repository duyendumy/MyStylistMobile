package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.auth.LoginRequest;
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
    private TextView signInTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userNameEditText = findViewById(R.id.userNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInTextView = findViewById(R.id.signInTextView);
        signUp();
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
            LoginRequest loginRequest = new LoginRequest(email, password);
            AuthService authService = retrofitService.createService(AuthService.class);
        });
    }
}


