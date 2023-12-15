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

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.OutfitClosetAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.UserOutfit;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UserOutfitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOutfitActivity extends AppCompatActivity {

    private LoadingAlert loadingAlert;
    private RetrofitService retrofitService;

    private Button addOutfitBtn;
    private ImageView imageBack;

    private GridView  outfitsGridView;

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

        setContentView(R.layout.activity_user_outfit);

        loadingAlert = new LoadingAlert(UserOutfitActivity.this);
        imageBack = findViewById(R.id.image_back);
        outfitsGridView = findViewById(R.id.outfitsGridView);
        addOutfitBtn = findViewById(R.id.addOutfitBtn);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(UserOutfitActivity.this, ClosetActivity.class);
            }
        });
        addOutfitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(UserOutfitActivity.this, AddOutfitActivity.class);
            }
        });
        loadUserOutfits();
    }
    public void loadUserOutfits(){
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

    private void populateUserOutfitListView(List<UserOutfit> userOutfits){
        OutfitClosetAdapter outfitClosetAdapter = new OutfitClosetAdapter( this, userOutfits);
        outfitsGridView.setAdapter(outfitClosetAdapter);
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}