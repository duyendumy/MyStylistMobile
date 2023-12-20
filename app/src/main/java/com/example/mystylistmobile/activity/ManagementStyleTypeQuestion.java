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

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.StyleTypeQuestionAdapter;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.StyleQuestion;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.StyleQuestionService;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagementStyleTypeQuestion extends AppCompatActivity {

    private RetrofitService retrofitService;

    private ListView listViewStyleTypeQuestion;

    private LoadingAlert loadingAlert;

    private Button addStyleTypeQuestionBtn;

    private BottomNavigationView bottomNavigationView;


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
        setContentView(R.layout.activity_management_style_type_question);

        loadingAlert = new LoadingAlert(ManagementStyleTypeQuestion.this);
        loadingAlert.startAlertDialog();


        listViewStyleTypeQuestion = findViewById(R.id.listViewStyleTypeQuestion);
        addStyleTypeQuestionBtn = findViewById(R.id.addStyleTypeQuestionBtn);

        addStyleTypeQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddStyleTypeQuestionActivity.class));
            }
        });



        StyleQuestionService styleQuestionService =  retrofitService.createService(StyleQuestionService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        styleQuestionService.getAllStyleTypeQuestions().enqueue(new Callback<ResponseModel<List<StyleQuestion>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<StyleQuestion>, ErrorDTO>> call, Response<ResponseModel<List<StyleQuestion>, ErrorDTO>> response) {
                if(response.body() != null){
                    loadingAlert.closeDialog();
                    populateStyleTypeQuestionListView(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<StyleQuestion>, ErrorDTO>> call, Throwable t) {

            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_style_type_question);
        handleBottomNavigation();
    }

    public void populateStyleTypeQuestionListView(List<StyleQuestion> styleQuestions){
        StyleTypeQuestionAdapter styleTypeQuestionAdapter = new StyleTypeQuestionAdapter( this, styleQuestions);
        listViewStyleTypeQuestion.setAdapter(styleTypeQuestionAdapter);
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
                    startActivity(new Intent(getApplicationContext(), ManagementItemActivity.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    finish();
                    return true;
                case R.id.bottom_seasonal_color_question:
                    startActivity(new Intent(getApplicationContext(), ManagementSeasonalColorQuestion.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    finish();
                    return true;
                case R.id.bottom_style_type_question:
                    return true;
            }
            return false;
        });
    }
}