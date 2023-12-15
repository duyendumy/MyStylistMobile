package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.SeasonalColorResponseDTO;
import com.example.mystylistmobile.dto.response.UserResponseDTO;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.Color;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.SeasonalColorService;
import com.example.mystylistmobile.service.UserService;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeasonalColorAboutActivity extends AppCompatActivity {

    private TextView detailTitle, detailDesc;

    private ImageView backImageView;

    private Button colorPaletteBtn;

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

        setContentView(R.layout.activity_seasonal_color_about);


        detailTitle = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDesc);
        backImageView = findViewById(R.id.image_back);
        colorPaletteBtn = findViewById(R.id.colorPaletteBtn);
        loadingAlert = new LoadingAlert(SeasonalColorAboutActivity.this);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeasonalColorAboutActivity.this, SeasonalColorActivity.class);
                startActivity(intent);
            }
        });
        colorPaletteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeasonalColorAboutActivity.this, ColorPaletteActivity.class);
                startActivity(intent);

            }
        });
        loadInformationOfSeasonalColor();


    }

    public void loadInformationOfSeasonalColor(){
        loadingAlert.startAlertDialog();

        UserService userService = retrofitService.createService(UserService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        userService.getUserInformation().enqueue(new Callback<ResponseModel<UserResponseDTO, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Response<ResponseModel<UserResponseDTO, ErrorDTO>> response) {
                if(response != null){
                    UserResponseDTO userResponseDTO = response.body().getResponse();
                    loadingAlert.closeDialog();
                    detailTitle.setText(userResponseDTO.getSeasonalColorName());
                    detailDesc.setText(userResponseDTO.getSeasonalColorDescription());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Throwable t) {
                Toast.makeText(SeasonalColorAboutActivity.this,"Get seasonal color information failed",Toast.LENGTH_SHORT).show();
                Logger.getLogger(SeasonalColorAboutActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });

        /*Long seasonalColorId = SessionManager.getInstance(this).getSeasonalColorId();
        SeasonalColorService seasonalColorService = retrofitService.createService(SeasonalColorService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        seasonalColorService.getSeasonalColorById(seasonalColorId).enqueue(new Callback<ResponseModel<SeasonalColorResponseDTO, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<SeasonalColorResponseDTO, ErrorDTO>> call, Response<ResponseModel<SeasonalColorResponseDTO, ErrorDTO>> response) {
                SeasonalColorResponseDTO seasonalColorResponseDTO = response.body().getResponse();
                if(seasonalColorResponseDTO != null){
                    loadingAlert.closeDialog();
                    detailTitle.setText(seasonalColorResponseDTO.getName());
                    detailDesc.setText(seasonalColorResponseDTO.getDescription());
                }
             //   return null;
            }

            @Override
            public void onFailure(Call<ResponseModel<SeasonalColorResponseDTO, ErrorDTO>> call, Throwable t) {
                Toast.makeText(SeasonalColorAboutActivity.this,"Get seasonal color information failed",Toast.LENGTH_SHORT).show();
                Logger.getLogger(SeasonalColorAboutActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });*/
    }



}