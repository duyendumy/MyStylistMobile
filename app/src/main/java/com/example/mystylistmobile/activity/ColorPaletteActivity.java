package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.ColorAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.SeasonalColorResponseDTO;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.SeasonalColorService;



import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ColorPaletteActivity extends AppCompatActivity {

    private GridView colorPaletteGridView;

    private ImageView backImageView;

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

        setContentView(R.layout.activity_color_palette);
        retrofitService = new RetrofitService();
        loadingAlert = new LoadingAlert(ColorPaletteActivity.this);
        colorPaletteGridView = findViewById(R.id.gridView);
        loadColorPalette();


        backImageView = findViewById(R.id.image_back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ColorPaletteActivity.this, SeasonalColorAboutActivity.class);
                startActivity(intent);
            }
        });

    }

    public void  populateListView(List<String> colors){
        ColorAdapter colorAdapter = new ColorAdapter(this, colors);
        colorPaletteGridView.setAdapter(colorAdapter);
    }

    public void loadColorPalette(){
        loadingAlert.startAlertDialog();
        Intent intent = getIntent();
        Long seasonalColorId = intent.getLongExtra("seasonalColorId", 1L);
        SeasonalColorService seasonalColorService = retrofitService.createService(SeasonalColorService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        seasonalColorService.getSeasonalColorById(seasonalColorId).enqueue(new Callback<ResponseModel<SeasonalColorResponseDTO, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<SeasonalColorResponseDTO, ErrorDTO>> call, Response<ResponseModel<SeasonalColorResponseDTO, ErrorDTO>> response) {
               if(response.body()!= null) {
                   loadingAlert.closeDialog();
                   List<String> colors = Arrays.asList(response.body().getResponse().getColorPalette().split(","));
                   populateListView(colors);
               }
            }

            @Override
            public void onFailure(Call<ResponseModel<SeasonalColorResponseDTO, ErrorDTO>> call, Throwable t) {
                Toast.makeText(ColorPaletteActivity.this,"Get color information failed",Toast.LENGTH_SHORT).show();
                Logger.getLogger(ColorPaletteActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });
    }
}