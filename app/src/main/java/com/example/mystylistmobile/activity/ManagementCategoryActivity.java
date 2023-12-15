package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.ManagementCategoryAdapter;
import com.example.mystylistmobile.dto.response.CategoryResponseDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.CategoryService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagementCategoryActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private ListView listviewCategories;

    private BottomNavigationView bottomNavigationView;

    private LoadingAlert loadingAlert;

    private Button addBtn;

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

        setContentView(R.layout.activity_management_category);

        loadingAlert = new LoadingAlert(ManagementCategoryActivity.this);
        loadingAlert.startAlertDialog();

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddCategoryActivity.class));
            }
        });

        listviewCategories = findViewById(R.id.listviewCategories);

        CategoryService itemCategoryService = retrofitService.createService(CategoryService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        itemCategoryService.getAllCategory().enqueue(new Callback<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> call, Response<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> response) {
                if(response != null){
                    loadingAlert.closeDialog();
                    populateCategoryListView(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> call, Throwable t) {
                Toast.makeText(ManagementCategoryActivity.this,"Get all categories failed!",Toast.LENGTH_SHORT).show();
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_category);
        handleBottomNavigation();
    }

    public void populateCategoryListView(List<CategoryResponseDTO> categoryResponseDTOList){
        ManagementCategoryAdapter managementCategoryAdapter = new ManagementCategoryAdapter( this, categoryResponseDTOList);
        listviewCategories.setAdapter(managementCategoryAdapter);
    }

    public void handleBottomNavigation(){
        this.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_user:
                    startActivity(new Intent(getApplicationContext(), UserActivity.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    finish();
                    return true;
                case R.id.bottom_category:
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