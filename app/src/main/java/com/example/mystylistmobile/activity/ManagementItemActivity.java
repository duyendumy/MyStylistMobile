package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.ManagementItemAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ItemResponseDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.ItemService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagementItemActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private ListView listviewItems;

    private BottomNavigationView bottomNavigationView;

    private LoadingAlert loadingAlert;

    private Button addBtn;


    @SuppressLint("WrongViewCast")
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

        setContentView(R.layout.activity_management_item);

        loadingAlert = new LoadingAlert(ManagementItemActivity.this);
        loadingAlert.startAlertDialog();
        listviewItems = findViewById(R.id.listviewItems);
        addBtn = findViewById(R.id.addBtn);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddItemActivity.class));
            }
        });
        ItemService itemService = retrofitService.createService(ItemService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        itemService.getAllItems().enqueue(new Callback<ResponseModel<List<ItemResponseDTO>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<ItemResponseDTO>, ErrorDTO>> call, Response<ResponseModel<List<ItemResponseDTO>, ErrorDTO>> response) {
                if(response != null){
                    loadingAlert.closeDialog();
                    populateItemListView(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<ItemResponseDTO>, ErrorDTO>> call, Throwable t) {
                Toast.makeText(ManagementItemActivity.this,"Get all items failed!",Toast.LENGTH_SHORT).show();
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_item);
        handleBottomNavigation();
    }

    public void populateItemListView(List<ItemResponseDTO> itemResponseDTOS){
        ManagementItemAdapter managementItemAdapter = new ManagementItemAdapter( this, itemResponseDTOS);
        listviewItems.setAdapter(managementItemAdapter);
    }

    public void handleBottomNavigation(){
        this.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_user:
                    startActivity(new Intent(getApplicationContext(), ManagementUserActivity.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    finish();
                    return true;
                case R.id.bottom_category:
                    startActivity(new Intent(getApplicationContext(), ManagementCategoryActivity.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    finish();
                    return true;
                case R.id.bottom_item:
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