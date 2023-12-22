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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.OutfitClosetAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.OnClickUserItemToDelete;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.UserOutfit;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UserItemService;
import com.example.mystylistmobile.service.UserOutfitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOutfitActivity extends AppCompatActivity implements OnClickUserItemToDelete {

    private LoadingAlert loadingAlert;
    private RetrofitService retrofitService;

    private Button addOutfitBtn;
    private ImageView imageBack;

    private GridView  outfitsGridView;

    private List<UserOutfit> userOutfits;

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
        retrofitService = new RetrofitService();
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
                userOutfits = response.body().getResponse();
                populateUserOutfitListView(userOutfits);
                loadingAlert.closeDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserOutfit>, ErrorDTO>> call, Throwable t) {

            }
        });
    }

    private void populateUserOutfitListView(List<UserOutfit> userOutfits){
        OutfitClosetAdapter outfitClosetAdapter = new OutfitClosetAdapter( this, userOutfits);
        outfitClosetAdapter.setOnClickOutfitToDelete(this);
        outfitsGridView.setAdapter(outfitClosetAdapter);
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public void deleteUserOutfit(UserOutfit userOutfit){
        UserOutfitService userOutfitService = retrofitService.createService(UserOutfitService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        loadingAlert.startAlertDialog();
        userOutfitService.deleteUserOutfit(userOutfit.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadingAlert.closeDialog();
                Toast.makeText(UserOutfitActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserOutfitActivity.this, UserOutfitActivity.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserOutfitActivity.this, "Delete failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickUserItemToDelete(int position) {
        UserOutfit deletedUserOutfit = userOutfits.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserOutfitActivity.this);
        builder.setMessage("Do you really want to delete this outfit?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteUserOutfit(deletedUserOutfit);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }
}