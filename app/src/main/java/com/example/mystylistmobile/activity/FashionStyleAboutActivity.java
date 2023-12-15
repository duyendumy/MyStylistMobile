package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.SeasonalColorResponseDTO;
import com.example.mystylistmobile.dto.response.StyleTypeResponseDTO;
import com.example.mystylistmobile.dto.response.UserResponseDTO;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.StyleTypeService;
import com.example.mystylistmobile.service.UserService;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FashionStyleAboutActivity extends AppCompatActivity {
    private ImageView backImageView;

    private TextView detailTitle, detailDesc;

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

        setContentView(R.layout.activity_fashion_style_about);
        detailTitle = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDesc);
        backImageView = findViewById(R.id.image_back);
        loadingAlert = new LoadingAlert(FashionStyleAboutActivity.this);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FashionStyleAboutActivity.this, FashionStyleActivity.class);
                startActivity(intent);
            }
        });
        loadInformationOfFashionStyleColor();
    }

    public void loadInformationOfFashionStyleColor(){
        loadingAlert.startAlertDialog();

        UserService userService = retrofitService.createService(UserService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        userService.getUserInformation().enqueue(new Callback<ResponseModel<UserResponseDTO, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Response<ResponseModel<UserResponseDTO, ErrorDTO>> response) {
                if(response != null){
                    UserResponseDTO userResponseDTO = response.body().getResponse();
                    loadingAlert.closeDialog();
                    detailTitle.setText(userResponseDTO.getStyleTypeName());
                    detailDesc.setText(userResponseDTO.getStyleTypeDescription());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Throwable t) {
                Toast.makeText(FashionStyleAboutActivity.this,"Get style type information failed",Toast.LENGTH_SHORT).show();
                Logger.getLogger(BodyTypeAboutActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });


    }


}