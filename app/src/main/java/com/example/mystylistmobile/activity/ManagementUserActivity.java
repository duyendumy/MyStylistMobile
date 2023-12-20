package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.UserAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.UserResponseDTO;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagementUserActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private ListView listviewUser;

    private BottomNavigationView bottomNavigationView;

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

        setContentView(R.layout.activity_user);
        loadingAlert = new LoadingAlert(ManagementUserActivity.this);
        loadingAlert.startAlertDialog();
        listviewUser = findViewById(R.id.listviewUser);


        UserService userService = retrofitService.createService(UserService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        userService.getAllUsers().enqueue(new Callback<ResponseModel<List<UserResponseDTO>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserResponseDTO>, ErrorDTO>> call, Response<ResponseModel<List<UserResponseDTO>, ErrorDTO>> response) {
                populateUserListView(response.body().getResponse());
                loadingAlert.closeDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserResponseDTO>, ErrorDTO>> call, Throwable t) {

            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_user);
        handleBottomNavigation();

    }

    private void populateUserListView(List<UserResponseDTO > userList){
        UserAdapter userAdapter = new UserAdapter( this, userList);
        listviewUser.setAdapter(userAdapter);
    }

    public void handleBottomNavigation(){
        this.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_user:
                    return true;
                case R.id.bottom_category:
                    startActivity(new Intent(getApplicationContext(), ManagementCategoryActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_item:
                    startActivity(new Intent(getApplicationContext(), ManagementItemActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_seasonal_color_question:
                    startActivity(new Intent(getApplicationContext(), ManagementSeasonalColorQuestion.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_style_type_question:
                    startActivity(new Intent(getApplicationContext(), ManagementStyleTypeQuestion.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }
}