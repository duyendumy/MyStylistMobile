package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.ScrollItemClosetAdapter;
import com.example.mystylistmobile.adapter.ScrollOutfitClosetAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.UserItem;
import com.example.mystylistmobile.model.UserOutfit;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UserItemService;
import com.example.mystylistmobile.service.UserOutfitService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollClosetActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMyItems;

    private RecyclerView recyclerViewMyOutfits;

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

        setContentView(R.layout.activity_scroll_closet);
        retrofitService = new RetrofitService();
        loadingAlert = new LoadingAlert(ScrollClosetActivity.this);
        recyclerViewMyItems = findViewById(R.id.recyclerViewMyItems);
        recyclerViewMyOutfits = findViewById(R.id.recyclerViewMyOutfits);
        imgAddItem = findViewById(R.id.imgAddItem);
        viewAllItems = findViewById(R.id.viewAllItems);
        viewAllOutfits = findViewById(R.id.viewAllOutfits);

        viewAllItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ScrollClosetActivity.this, UserItemActivity.class);
            }
        });

        viewAllOutfits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ScrollClosetActivity.this, UserOutfitActivity.class);
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
        ScrollItemClosetAdapter itemClosetAdapter = new ScrollItemClosetAdapter( this, userItemList);
        recyclerViewMyItems.setAdapter(itemClosetAdapter);
    }

    private void populateUserOutfitListView(List<UserOutfit> userOutfits){
        ScrollOutfitClosetAdapter outfitClosetAdapter = new ScrollOutfitClosetAdapter(this, userOutfits);
        recyclerViewMyOutfits.setAdapter(outfitClosetAdapter);

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