package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.UserItemAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.OnClickUserItemToDelete;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.UserItem;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UserItemService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserItemActivity extends AppCompatActivity implements OnClickUserItemToDelete {

    private GridView itemClosetGridView;

    private LoadingAlert loadingAlert;

    private RetrofitService retrofitService;

    private Button addItemBtn;

    private ImageView imageBack;

    private List<UserItem> userItems;


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
        retrofitService = new RetrofitService();
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
                redirectActivity(UserItemActivity.this, ScrollClosetActivity.class);
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
                userItems = response.body().getResponse();
                populateUserItemListView(userItems);
                loadingAlert.closeDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserItem>, ErrorDTO>> call, Throwable t) {

            }
        });
    }

    private void populateUserItemListView(List<UserItem> userItemList){
        UserItemAdapter userItemAdapter = new UserItemAdapter( this, userItemList);
        userItemAdapter.setOnClickUserItemToDelete(this);
        itemClosetGridView.setAdapter(userItemAdapter);
    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public void deleteUserItem(UserItem deletedUserItem){
        UserItemService userItemService = retrofitService.createService(UserItemService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        loadingAlert.startAlertDialog();
        userItemService.deleteUserItem(deletedUserItem.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadingAlert.closeDialog();
                Toast.makeText(UserItemActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserItemActivity.this, UserItemActivity.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserItemActivity.this, "Delete failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickUserItemToDelete(int position) {
        UserItem deletedUserItem = userItems.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserItemActivity.this);
        builder.setMessage("Do you really want to delete this item?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteUserItem(deletedUserItem);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}