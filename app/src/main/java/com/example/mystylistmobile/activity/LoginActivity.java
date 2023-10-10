package com.example.mystylistmobile.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.auth.LoginRequest;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.RegisterV2ResponseDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.TokenDTO;
import com.example.mystylistmobile.dto.response.UserPaging;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.service.AuthService;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UserService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private RetrofitService retrofitService;
    private EditText emailEditText, passwordEditText;
    private Button loginBtn;
    private TextView signUpTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.inputEmail);
        passwordEditText = findViewById(R.id.inputPassword);
        loginBtn = findViewById(R.id.logInBtn);
        signUpTextView = findViewById(R.id.signUpTextView);
        this.login();
    }

    public void login(){
        loginBtn.setOnClickListener(view -> {
            String email = String.valueOf(emailEditText.getText());
            String password = String.valueOf(passwordEditText.getText());
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
            retrofitService = new RetrofitService();
            sessionManager = new SessionManager(this);
            LoginRequest loginRequest = new LoginRequest(email, password);
            AuthService authService = retrofitService.createService(AuthService.class);
            authService.login(loginRequest).enqueue(new Callback<RegisterV2ResponseDTO>() {

                @Override
                public void onResponse(Call<RegisterV2ResponseDTO> call, Response<RegisterV2ResponseDTO> response) {
                    RegisterV2ResponseDTO loginResponse = response.body();
                    if(loginResponse != null) {
                        TokenDTO accessToken = loginResponse.getTokens().getAccess();
                        sessionManager.saveAuthToken(accessToken.getToken());
                        Toast.makeText(LoginActivity.this, "Login successfully" + loginResponse.getUser().toString(), Toast.LENGTH_SHORT).show();
                        getAllUsers();
                    }
                }

                @Override
                public void onFailure(Call<RegisterV2ResponseDTO> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,"Sign in failed!",Toast.LENGTH_SHORT).show();
                    Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
                }
            });
        });

    }

    public void getAllUsers(){
        UserService userService = retrofitService.createService(UserService.class, SessionManager.getInstance(this).getAuthToken());
        userService.getAllUsers(0,4, "id").enqueue(new Callback<ResponseModel<UserPaging, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserPaging, ErrorDTO>> call, Response<ResponseModel<UserPaging, ErrorDTO>> response) {
                if(response != null) {
                    Toast.makeText(LoginActivity.this, "Get all user API" + response.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println("Get all user API" + response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<ResponseModel<UserPaging, ErrorDTO>> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Get all users failed!",Toast.LENGTH_SHORT).show();
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });
    }
}
