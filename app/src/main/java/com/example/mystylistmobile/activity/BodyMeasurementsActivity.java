package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.UpdateUserDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.UserResponseDTO;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.BodyMeasurementService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BodyMeasurementsActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private LoadingAlert loadingAlert;
    private EditText inputBust, inputWaist, inputHighHip, inputHip;

  /*  private Float fInputBust, fInputWaist, fInputHighHip, fInputHip;*/

    private Button cancelButton, saveButton;



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

        setContentView(R.layout.activity_body_measurements);
        retrofitService = new RetrofitService();
        loadingAlert = new LoadingAlert(BodyMeasurementsActivity.this);

        inputBust = findViewById(R.id.inputBust);
        inputWaist = findViewById(R.id.inputWaist);
        inputHighHip = findViewById(R.id.inputHighHip);
        inputHip = findViewById(R.id.inputHip);

        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BodyMeasurementsActivity.this, BodyTypeActivity.class);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickSaveButton();
            }
        });

    }

    public void handleClickSaveButton(){
        if(inputBust.getText().toString().trim().equalsIgnoreCase("")){
            inputBust.setError("Please enter your bust");
            return;
        }
        if(inputWaist.getText().toString().trim().equalsIgnoreCase("")){
            inputWaist.setError("Please enter your waist");
            return;
        }
        if(inputHighHip.getText().toString().trim().equalsIgnoreCase("")){
            inputHighHip.setError("Please enter your high hip");
            return;
        }
        if(inputHip.getText().toString().trim().equalsIgnoreCase("")){
            inputHip.setError("Please enter your hip");
            return;
        }

        Float fInputBust = Float.valueOf(inputBust.getText().toString());
        Float fInputWaist = Float.valueOf(inputWaist.getText().toString());
        Float fInputHighHip = Float.valueOf(inputHighHip.getText().toString());
        Float fInputHip = Float.valueOf(inputHip.getText().toString());

        Float minusOfBustHip = fInputBust - fInputHip;
        Float minusOFBustWaist = fInputBust - fInputWaist;
        Float minusOfHipWaist = fInputHip - fInputWaist;
        Float minusOfHighHipWaist = fInputHighHip - fInputWaist;
        Float minusOfHipBust= fInputHip - fInputBust;
        String result = "";
        if(minusOfBustHip <= 1 && minusOfHipBust <= 3.6 && minusOFBustWaist >= 9 && minusOfHipWaist >= 10){
            Toast.makeText(getApplicationContext(), "Your body shape is hourglass", Toast.LENGTH_SHORT).show();
            result = "hourglass";
        }
        else if(minusOfHipBust >= 3.6 && minusOfHipBust < 10 && minusOfHipWaist >= 9 && minusOfHighHipWaist >= 9){
            Toast.makeText(getApplicationContext(), "Your body shape is bottom hourglass", Toast.LENGTH_SHORT).show();
            result = "bottom hourglass";
        }
        else if(minusOfBustHip > 1 && minusOfBustHip < 10 && minusOFBustWaist >= 9){
            Toast.makeText(getApplicationContext(), "Your body shape is top hourglass", Toast.LENGTH_SHORT).show();
            result = "top hourglass";
        }
        else if(minusOfHipBust > 2 && minusOfHipWaist >= 7 && minusOfHighHipWaist > 1.193){
            Toast.makeText(getApplicationContext(), "Your body shape is spoon", Toast.LENGTH_SHORT).show();
            result = "spoon";
        }
        else if(minusOfHipBust >= 3.6 && minusOfHipWaist < 9){
            Toast.makeText(getApplicationContext(), "Your body shape is triangle", Toast.LENGTH_SHORT).show();
            result = "triangle";
        }
        else if(minusOfBustHip >= 3.6 && minusOFBustWaist < 9){
            Toast.makeText(getApplicationContext(), "Your body shape is inverted triangle", Toast.LENGTH_SHORT).show();
            result = "inverted triangle";
        }
        else if(minusOfHipBust >= 3.6 && minusOfBustHip < 3.6 && minusOFBustWaist < 9 && minusOfHipWaist < 10){
            Toast.makeText(getApplicationContext(), "Your body shape is rectangle", Toast.LENGTH_SHORT).show();
            result = "rectangle";
        }
        else{
            Toast.makeText(getApplicationContext(), "Your body shape is rectangle", Toast.LENGTH_SHORT).show();
            result = "rectangle";
        }

        submitMeasurements(result,fInputBust,fInputHighHip,fInputHip,fInputWaist);
    }

    public void submitMeasurements(String result, Float fInputBust, Float fInputHighHip, Float fInputHip, Float fInputWaist){
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setBust(fInputBust);
        updateUserDTO.setHighHip(fInputHighHip);
        updateUserDTO.setHip(fInputHip);
        updateUserDTO.setWaist(fInputWaist);
        updateUserDTO.setBodyShape(result);
        loadingAlert.startAlertDialog();
        BodyMeasurementService bodyMeasurementService = retrofitService.createService(BodyMeasurementService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        bodyMeasurementService.submitBodyMeasurement(updateUserDTO).enqueue(new Callback<ResponseModel<UserResponseDTO, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Response<ResponseModel<UserResponseDTO, ErrorDTO>> response) {
                if(response.body() != null){
                    loadingAlert.closeDialog();
                    startActivity(new Intent(BodyMeasurementsActivity.this, BodyTypeAboutActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Failed to update user. Please try again.", Toast.LENGTH_SHORT).show();
                loadingAlert.closeDialog();
            }
        });
    }
}