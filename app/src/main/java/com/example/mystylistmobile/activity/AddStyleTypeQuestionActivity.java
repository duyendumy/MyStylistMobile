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
import com.example.mystylistmobile.dto.AddStyleQuestionDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.StyleQuestion;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.ContrastQuestionService;
import com.example.mystylistmobile.service.StyleQuestionService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStyleTypeQuestionActivity extends AppCompatActivity {
    private RetrofitService retrofitService;

    private LoadingAlert loadingAlert;

    private Button saveButton,cancelButton;

    private EditText addQuestion, addClassicOption, addNaturalOption, addSexyOption, addDramaticOption, addFeminineOption, addElegantOption;

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

        setContentView(R.layout.activity_add_style_type_question);
        loadingAlert = new LoadingAlert(AddStyleTypeQuestionActivity.this);

        addQuestion = findViewById(R.id.addQuestion);
        addClassicOption = findViewById(R.id.addClassicOption);
        addNaturalOption = findViewById(R.id.addNaturalOption);
        addSexyOption = findViewById(R.id.addSexyOption);
        addDramaticOption = findViewById(R.id.addDramaticOption);
        addFeminineOption = findViewById(R.id.addFeminineOption);
        addElegantOption = findViewById(R.id.addElegantOption);


        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStyleTypeQuestion();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManagementStyleTypeQuestion.class));
            }
        });
    }
    public void saveStyleTypeQuestion() {
        if (addQuestion.getText().toString().trim().equalsIgnoreCase("")) {
            addQuestion.setError("Please enter question");
            return;
        }
        if (addClassicOption.getText().toString().trim().equalsIgnoreCase("")) {
            addClassicOption.setError("Please enter classic option");
            return;
        }
        if (addNaturalOption.getText().toString().trim().equalsIgnoreCase("")) {
            addNaturalOption.setError("Please enter natural option");
            return;
        }
        if (addSexyOption.getText().toString().trim().equalsIgnoreCase("")) {
            addSexyOption.setError("Please enter sexy option");
            return;
        }
        if (addDramaticOption.getText().toString().trim().equalsIgnoreCase("")) {
            addDramaticOption.setError("Please enter dramatic option");
            return;
        }
        if (addFeminineOption.getText().toString().trim().equalsIgnoreCase("")) {
            addFeminineOption.setError("Please enter feminine option");
            return;
        }
        if (addElegantOption.getText().toString().trim().equalsIgnoreCase("")) {
            addElegantOption.setError("Please enter elegant option");
            return;
        }
        loadingAlert.startAlertDialog();

        AddStyleQuestionDTO addStyleQuestionDTO = new AddStyleQuestionDTO();
        addStyleQuestionDTO.setQuestion(String.valueOf(addQuestion.getText()));
        addStyleQuestionDTO.setClassicOption(String.valueOf(addClassicOption.getText()));
        addStyleQuestionDTO.setNaturalOption(String.valueOf(addNaturalOption.getText()));
        addStyleQuestionDTO.setSexyOption(String.valueOf(addSexyOption.getText()));
        addStyleQuestionDTO.setDramaticOption(String.valueOf(addDramaticOption.getText()));
        addStyleQuestionDTO.setFeminineOption(String.valueOf(addFeminineOption.getText()));
        addStyleQuestionDTO.setElegantOption(String.valueOf(addElegantOption.getText()));

        StyleQuestionService styleQuestionService = retrofitService.createService(StyleQuestionService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        styleQuestionService.addStyleTypeQuestion(addStyleQuestionDTO).enqueue(new Callback<ResponseModel<StyleQuestion, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<StyleQuestion, ErrorDTO>> call, Response<ResponseModel<StyleQuestion, ErrorDTO>> response) {
                if(response.body() != null){
                    loadingAlert.closeDialog();
                    startActivity(new Intent(getApplicationContext(), ManagementStyleTypeQuestion.class));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<StyleQuestion, ErrorDTO>> call, Throwable t) {

            }
        });
    }
}