package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.AddContrastQuestionDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.ContrastQuestion;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.ContrastQuestionService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContrastQuestionActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private LoadingAlert loadingAlert;

    private Button saveButton,cancelButton;
    private EditText addQuestion, addHighOption, addLowOption, addMediumOption;

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

        setContentView(R.layout.activity_add_contrast_question);
        retrofitService = new RetrofitService();
        loadingAlert = new LoadingAlert(AddContrastQuestionActivity.this);

        addQuestion = findViewById(R.id.addQuestion);
        addHighOption = findViewById(R.id.addHighOption);
        addLowOption = findViewById(R.id.addLowOption);
        addMediumOption = findViewById(R.id.addMediumOption);

        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveContrastQuestion();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ContrastQuestionActivity.class));
            }
        });

    }

    public void saveContrastQuestion(){
        if(addQuestion.getText().toString().trim().equalsIgnoreCase("")){
            addQuestion.setError("Please enter question");
            return;
        }
        if(addHighOption.getText().toString().trim().equalsIgnoreCase("")){
            addHighOption.setError("Please enter high option");
            return;
        }
        if(addLowOption.getText().toString().trim().equalsIgnoreCase("")){
            addLowOption.setError("Please enter low option");
            return;
        }
        if(addMediumOption.getText().toString().trim().equalsIgnoreCase("")){
            addMediumOption.setError("Please enter medium option");
            return;
        }

        loadingAlert.startAlertDialog();
        AddContrastQuestionDTO addContrastQuestionDTO = new AddContrastQuestionDTO();
        addContrastQuestionDTO.setQuestion(String.valueOf(addQuestion.getText()));
        addContrastQuestionDTO.setHighOption(String.valueOf(addHighOption.getText()));
        addContrastQuestionDTO.setLowOption(String.valueOf(addLowOption.getText()));
        addContrastQuestionDTO.setMediumOption(String.valueOf(addMediumOption.getText()));

        ContrastQuestionService contrastQuestionService = retrofitService.createService(ContrastQuestionService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        contrastQuestionService.addContrastQuestion(addContrastQuestionDTO).enqueue(new Callback<ResponseModel<ContrastQuestion, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<ContrastQuestion, ErrorDTO>> call, Response<ResponseModel<ContrastQuestion, ErrorDTO>> response) {
               if(response.body() != null){
                   loadingAlert.closeDialog();
                   startActivity(new Intent(getApplicationContext(), ContrastQuestionActivity.class));
               }
            }

            @Override
            public void onFailure(Call<ResponseModel<ContrastQuestion, ErrorDTO>> call, Throwable t) {

            }
        });


    }
}