package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.mystylistmobile.service.UndertoneQuestionService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BodyMeasurementsActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private LoadingAlert loadingAlert;
    private EditText inputBust, inputWaist, inputHighHip, inputHip;

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
        Float userBust = Float.valueOf(inputBust.getText().toString());
        Float userWaist = Float.valueOf(inputWaist.getText().toString());
        Float userHighHip = Float.valueOf(inputHighHip.getText().toString());
        Float userHip = Float.valueOf(inputHip.getText().toString());
        Float minusOfBustHip = userBust - userHip;
        Float minusOFBustWaist = userBust - userWaist;
        Float minusOfHipWaist = userHip - userWaist;
        Float minusOfHighHipWaist = userHighHip - userWaist;
        Float minusOfHipBust= userHip - userBust;
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

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setBust(userBust);
        updateUserDTO.setHighHip(userHighHip);
        updateUserDTO.setHip(userHip);
        updateUserDTO.setWaist(userWaist);
        updateUserDTO.setBodyShape(result);
        loadingAlert.startAlertDialog();
        BodyMeasurementService bodyMeasurementService = retrofitService.createService(BodyMeasurementService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        bodyMeasurementService.submitBodyMeasurement(updateUserDTO).enqueue(new Callback<ResponseModel<UserResponseDTO, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Response<ResponseModel<UserResponseDTO, ErrorDTO>> response) {
                if(response.body() != null){
                    loadingAlert.closeDialog();
                    startActivity(new Intent(getApplicationContext(), BodyTypeAboutActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserResponseDTO, ErrorDTO>> call, Throwable t) {

            }
        });
    }
}