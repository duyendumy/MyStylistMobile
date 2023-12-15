package com.example.mystylistmobile.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.mystylistmobile.dto.response.UserResponseDTO;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.service.AuthService;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private RetrofitService retrofitService;
    private EditText emailEditText, passwordEditText;
    private Button loginBtn;
    private TextView signUpTextView;
    private LoadingAlert loadingAlert;
    private CheckBox saveLoginCheckBox;

    private Boolean  saveLogin;


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

        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.inputEmail);
        passwordEditText = findViewById(R.id.inputPassword);
        loginBtn = findViewById(R.id.logInBtn);
        signUpTextView = findViewById(R.id.signUpTextView);
        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
        sessionManager = new SessionManager(this);
        saveLogin = sessionManager.getSaveLogin();
        if(saveLogin == true){
            emailEditText.setText(sessionManager.getEmail());
            passwordEditText.setText(sessionManager.getPassword());
            saveLoginCheckBox.setChecked(true);
        }
        loadingAlert = new LoadingAlert(LoginActivity.this);
        login();
    }

    public void login(){
        loginBtn.setOnClickListener(view -> {
            String email = String.valueOf(emailEditText.getText());
            String password = String.valueOf(passwordEditText.getText());
            if(saveLoginCheckBox.isChecked()){
                sessionManager.setEmail(email);
                sessionManager.setPassword(password);
                sessionManager.setSaveLogin(true);
            }
            else{
                sessionManager.clear();
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
            loadingAlert.startAlertDialog();

            retrofitService = new RetrofitService();

            LoginRequest loginRequest = new LoginRequest(email, password);
            AuthService authService = retrofitService.createService(AuthService.class);
            authService.login(loginRequest).enqueue(new Callback<RegisterV2ResponseDTO>() {
                @Override
                public void onResponse(Call<RegisterV2ResponseDTO> call, Response<RegisterV2ResponseDTO> response) {
                    RegisterV2ResponseDTO loginResponse = response.body();
                    if(loginResponse != null) {
                        loadingAlert.closeDialog();
                        TokenDTO accessToken = loginResponse.getTokens().getAccess();
                        TokenDTO refreshToken = loginResponse.getTokens().getRefresh();
                        UserResponseDTO userResponseDTO = loginResponse.getUser();
                        sessionManager.saveUserInfo(accessToken.getToken(), refreshToken.getToken(), userResponseDTO.getName(), userResponseDTO.getSeasonalColorName(),userResponseDTO.getSeasonalColorId(), userResponseDTO.getStyleTypeName(), userResponseDTO.getStyleTypeId(), userResponseDTO.getBodyShapeName(), userResponseDTO.getBodyShapeId());
                        Toast.makeText(LoginActivity.this, "Login successfully" , Toast.LENGTH_SHORT).show();
                        List<String> roles = parseRoles(loginResponse.getUser().getRoles());
                        if(roles.contains("ROLE_ADMIN")){
                            Intent intent = new Intent(LoginActivity.this, GetStartedActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(LoginActivity.this, DNAActivity.class);
                            startActivity(intent);
                        }
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
    private List<String> parseRoles(String input) {
        String[] rolesArray = input.split(",");
        List<String> rolesList = Arrays.asList(rolesArray);
        return rolesList;
    }


    /*public void getAllUsers(){
        UserService userService = retrofitService.createService(UserService.class, SessionManager.getInstance(this).getUserToken());
        userService.getAllUsers(0,4, "id").enqueue(new Callback<ResponseModel<UserPaging, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserPaging, ErrorDTO>> call, Response<ResponseModel<UserPaging, ErrorDTO>> response) {
                if(response != null) {
                    Toast.makeText(LoginActivity.this, "Get all user API" + response.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println("Get all user API" + response.body().toString());
                }
                //return null;
            }
            @Override
            public void onFailure(Call<ResponseModel<UserPaging, ErrorDTO>> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Get all users failed!",Toast.LENGTH_SHORT).show();
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });
    }*/
}
