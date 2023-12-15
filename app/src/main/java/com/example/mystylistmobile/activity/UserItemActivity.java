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
import com.example.mystylistmobile.adapter.UserItemAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.UserItem;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UserItemService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserItemActivity extends AppCompatActivity {

    private GridView itemClosetGridView;

    private LoadingAlert loadingAlert;

    private RetrofitService retrofitService;

    private Button addItemBtn;

    private ImageView imageBack;

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
        setContentView(R.layout.activity_user_item);
        loadingAlert = new LoadingAlert(UserItemActivity.this);

        itemClosetGridView = findViewById(R.id.gridViewMyItems);
        addItemBtn = findViewById(R.id.addOutfitBtn);
        imageBack = findViewById(R.id.image_back);

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(UserItemActivity.this, AddUserItemActivity.class);
            }
        });
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(UserItemActivity.this, ClosetActivity.class);
            }
        });
        loadUserItem();

    }

    public void loadUserItem(){
        loadingAlert.startAlertDialog();
        UserItemService userItemService = retrofitService.createService(UserItemService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        userItemService.getAllUserItem().enqueue(new Callback<ResponseModel<List<UserItem>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserItem>, ErrorDTO>> call, Response<ResponseModel<List<UserItem>, ErrorDTO>> response) {
                populateUserItemListView(response.body().getResponse());
                loadingAlert.closeDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserItem>, ErrorDTO>> call, Throwable t) {

            }
        });
    }

    private void populateUserItemListView(List<UserItem> userItemList){
        UserItemAdapter userItemAdapter = new UserItemAdapter( this, userItemList);
        itemClosetGridView.setAdapter(userItemAdapter);
    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

}