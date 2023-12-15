package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.ItemClosetAdapter;
import com.example.mystylistmobile.adapter.OutfitClosetAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.ItemCloset;
import com.example.mystylistmobile.model.OutfitCloset;
import com.example.mystylistmobile.model.UserItem;
import com.example.mystylistmobile.model.UserOutfit;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.CategoryService;
import com.example.mystylistmobile.service.UserItemService;
import com.example.mystylistmobile.service.UserOutfitService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClosetActivity extends AppCompatActivity {

    private GridView itemClosetGridView;

    private GridView outfitClosetGridView;

    private BottomNavigationView bottomNavigationView;

    private ImageView imgAddItem;

    private LoadingAlert loadingAlert;

    private TextView viewAllItems, viewAllOutfits;

    private RetrofitService retrofitService;

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

        setContentView(R.layout.activity_closet);
        loadingAlert = new LoadingAlert(ClosetActivity.this);
        itemClosetGridView = findViewById(R.id.gridViewMyItems);
        outfitClosetGridView = findViewById(R.id.gridViewTodayOutfits);
        imgAddItem = findViewById(R.id.imgAddItem);
        viewAllItems = findViewById(R.id.viewAllItems);
        viewAllOutfits = findViewById(R.id.viewAllOutfits);

        viewAllItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ClosetActivity.this, UserItemActivity.class);
            }
        });

        viewAllOutfits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ClosetActivity.this, UserOutfitActivity.class);
            }
        });

        loadUserItem();
        loadUserOutfit();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_closet);
        handleBottomNavigation();
    }

    public void loadUserItem(){
        UserItemService userItemService = retrofitService.createService(UserItemService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        userItemService.getAllUserItem().enqueue(new Callback<ResponseModel<List<UserItem>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserItem>, ErrorDTO>> call, Response<ResponseModel<List<UserItem>, ErrorDTO>> response) {
                populateUserItemListView(response.body().getResponse());
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserItem>, ErrorDTO>> call, Throwable t) {

            }
        });
    }

    public void loadUserOutfit(){
        loadingAlert.startAlertDialog();
        UserOutfitService userOutfitService = retrofitService.createService(UserOutfitService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        userOutfitService.getAllUserOutfits().enqueue(new Callback<ResponseModel<List<UserOutfit>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserOutfit>, ErrorDTO>> call, Response<ResponseModel<List<UserOutfit>, ErrorDTO>> response) {
                populateUserOutfitListView(response.body().getResponse());
                loadingAlert.closeDialog();
            }
            @Override
            public void onFailure(Call<ResponseModel<List<UserOutfit>, ErrorDTO>> call, Throwable t) {

            }
        });
    }

    private void populateUserItemListView(List<UserItem> userItemList){
        ItemClosetAdapter itemClosetAdapter = new ItemClosetAdapter( this, userItemList);
        itemClosetGridView.setAdapter(itemClosetAdapter);
    }
    private void populateUserOutfitListView(List<UserOutfit> userOutfits){
        OutfitClosetAdapter outfitClosetAdapter = new OutfitClosetAdapter(this, userOutfits);
        outfitClosetGridView.setAdapter(outfitClosetAdapter);

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

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}