package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.CategoryAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.CategoryPaging;
import com.example.mystylistmobile.dto.response.CategoryResponseDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.CategoryService;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    private ListView categoryListView;

    private BottomNavigationView bottomNavigationView;

    private RetrofitService retrofitService;

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

        setContentView(R.layout.activity_category);
        categoryListView = findViewById(R.id.listview);
        loadingAlert = new LoadingAlert(CategoryActivity.this);


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_category);
        handleBottomNavigation();
        loadParentCategory();
    }
    public void handleBottomNavigation(){
        this.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_dna:
                    startActivity(new Intent(getApplicationContext(), DNAActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                case R.id.bottom_category:
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
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }

    public void populateListView(List<CategoryResponseDTO> categories){
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        categoryListView.setAdapter(categoryAdapter);
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CategoryActivity.this, ItemCategoryActivity.class);
                intent.putExtra("idCategory", categories.get(i).getId());
                startActivity(intent);
            }
        });
    }

    public void loadParentCategory(){
        loadingAlert.startAlertDialog();
        CategoryService itemCategoryService = retrofitService.createService(CategoryService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        itemCategoryService.getAllCategory().enqueue(new Callback<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> call, Response<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> response) {
                if(response != null){
                    loadingAlert.closeDialog();
                    populateListView(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> call, Throwable t) {
                Toast.makeText(CategoryActivity.this,"Get all parent categories failed!",Toast.LENGTH_SHORT).show();
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });
       /* itemCategoryService.getAllCategory((long) -1, 0, 8, "id").enqueue(new Callback<ResponseModel<CategoryPaging, ErrorDTO>>() {

            @Override
            public void onResponse(Call<ResponseModel<CategoryPaging, ErrorDTO>> call, Response<ResponseModel<CategoryPaging, ErrorDTO>> response) {
                if(response != null){
                    loadingAlert.closeDialog();
                    populateListView(response.body().getResponse().getCategories());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<CategoryPaging, ErrorDTO>> call, Throwable t) {
                Toast.makeText(CategoryActivity.this,"Get all parent categories failed!",Toast.LENGTH_SHORT).show();
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });*/

    }
}