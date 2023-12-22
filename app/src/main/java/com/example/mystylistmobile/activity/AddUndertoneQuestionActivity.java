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
import com.example.mystylistmobile.dto.AddUndertoneQuestionDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.UndertoneQuestion;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.CategoryService;
import com.example.mystylistmobile.service.UndertoneQuestionService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUndertoneQuestionActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private LoadingAlert loadingAlert;

    private EditText addQuestion, addCoolOption, addWarmOption, addNeutralOption;

    private Button saveButton,cancelButton;

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

        setContentView(R.layout.activity_add_undertone_question);
        retrofitService = new RetrofitService();
        loadingAlert = new LoadingAlert(AddUndertoneQuestionActivity.this);

        addQuestion = findViewById(R.id.addQuestion);
        addCoolOption = findViewById(R.id.addCoolOption);
        addWarmOption = findViewById(R.id.addWarmOption);
        addNeutralOption = findViewById(R.id.addNeutralOption);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUndertoneQuestion();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UndertoneQuestionActivity.class));
            }
        });


    }

    public void saveUndertoneQuestion(){
        if(addQuestion.getText().toString().trim().equalsIgnoreCase("")){
            addQuestion.setError("Please enter question");
            return;
        }
        if(addCoolOption.getText().toString().trim().equalsIgnoreCase("")){
            addCoolOption.setError("Please enter cool option");
            return;
        }
        if(addWarmOption.getText().toString().trim().equalsIgnoreCase("")){
            addWarmOption.setError("Please enter warm option");
            return;
        }
        if(addNeutralOption.getText().toString().trim().equalsIgnoreCase("")){
            addNeutralOption.setError("Please enter neutral option");
            return;
        }
        loadingAlert.startAlertDialog();
        AddUndertoneQuestionDTO undertoneQuestion = new AddUndertoneQuestionDTO();
        undertoneQuestion.setQuestion(String.valueOf(addQuestion.getText()));
        undertoneQuestion.setCoolOption(String.valueOf(addCoolOption.getText()));
        undertoneQuestion.setWarmOption(String.valueOf(addWarmOption.getText()));
        undertoneQuestion.setNeutralOption(String.valueOf(addNeutralOption.getText()));

        UndertoneQuestionService undertoneQuestionService = retrofitService.createService(UndertoneQuestionService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        undertoneQuestionService.addUndertoneQuestions(undertoneQuestion).enqueue(new Callback<ResponseModel<UndertoneQuestion, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<UndertoneQuestion, ErrorDTO>> call, Response<ResponseModel<UndertoneQuestion, ErrorDTO>> response) {
                if(response.body() != null){
                    loadingAlert.closeDialog();
                    startActivity(new Intent(getApplicationContext(), UndertoneQuestionActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UndertoneQuestion, ErrorDTO>> call, Throwable t) {

            }
        });
    }
}