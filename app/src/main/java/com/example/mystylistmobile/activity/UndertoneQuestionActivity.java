package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.UndertoneQuestionAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.UndertoneQuestion;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UndertoneQuestionService;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UndertoneQuestionActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private ListView listViewUndertoneQuestion;

    private ImageView image_back;

    private LoadingAlert loadingAlert;

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

        setContentView(R.layout.activity_undertone_question);
        loadingAlert = new LoadingAlert(UndertoneQuestionActivity.this);
        loadingAlert.startAlertDialog();

        listViewUndertoneQuestion = findViewById(R.id.listViewUndertoneQuestion);
        image_back = findViewById(R.id.image_back);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManagementSeasonalColorQuestion.class));
            }
        });

        UndertoneQuestionService undertoneQuestionService =  retrofitService.createService(UndertoneQuestionService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        undertoneQuestionService.getAllUndertoneQuestions().enqueue(new Callback<ResponseModel<List<UndertoneQuestion>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UndertoneQuestion>, ErrorDTO>> call, Response<ResponseModel<List<UndertoneQuestion>, ErrorDTO>> response) {
                loadingAlert.closeDialog();
                populateUndertoneQuestionListView(response.body().getResponse());
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UndertoneQuestion>, ErrorDTO>> call, Throwable t) {

            }
        });
    }
    private void populateUndertoneQuestionListView(List<UndertoneQuestion> undertoneQuestions){
        UndertoneQuestionAdapter userItemAdapter = new UndertoneQuestionAdapter( this, undertoneQuestions);
        listViewUndertoneQuestion.setAdapter(userItemAdapter);
    }
}