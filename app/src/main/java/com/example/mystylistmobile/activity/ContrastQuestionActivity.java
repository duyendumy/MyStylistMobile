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
import com.example.mystylistmobile.adapter.ContrastQuestionAdapter;
import com.example.mystylistmobile.adapter.UndertoneQuestionAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.ContrastQuestion;
import com.example.mystylistmobile.model.UndertoneQuestion;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.ContrastQuestionService;
import com.example.mystylistmobile.service.UndertoneQuestionService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContrastQuestionActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private ListView listViewContrastQuestion;

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

        setContentView(R.layout.activity_contrast_question);

        loadingAlert = new LoadingAlert(ContrastQuestionActivity.this);
        loadingAlert.startAlertDialog();

        listViewContrastQuestion = findViewById(R.id.listViewContrastQuestion);
        image_back = findViewById(R.id.image_back);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManagementSeasonalColorQuestion.class));
            }
        });

        ContrastQuestionService contrastQuestionService = retrofitService.createService(ContrastQuestionService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        contrastQuestionService.getAllContrastQuestions().enqueue(new Callback<ResponseModel<List<ContrastQuestion>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<ContrastQuestion>, ErrorDTO>> call, Response<ResponseModel<List<ContrastQuestion>, ErrorDTO>> response) {
                loadingAlert.closeDialog();
                populateUndertoneQuestionListView(response.body().getResponse());
            }

            @Override
            public void onFailure(Call<ResponseModel<List<ContrastQuestion>, ErrorDTO>> call, Throwable t) {

            }
        });
    }

    private void populateUndertoneQuestionListView(List<ContrastQuestion> contrastQuestions){
        ContrastQuestionAdapter contrastQuestionAdapter = new ContrastQuestionAdapter( this, contrastQuestions);
        listViewContrastQuestion.setAdapter(contrastQuestionAdapter);
    }
}