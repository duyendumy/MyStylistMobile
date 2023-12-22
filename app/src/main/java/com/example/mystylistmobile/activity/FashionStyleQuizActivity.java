package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.SubmitStyleTypeQuizDTO;
import com.example.mystylistmobile.dto.UserAnswerDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.AttemptQuiz;
import com.example.mystylistmobile.model.StyleQuestion;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.AttemptQuizService;
import com.example.mystylistmobile.service.StyleQuestionService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FashionStyleQuizActivity extends AppCompatActivity {
    private int currentFashionStyleQuestionIndex = 0;
    private TextView number_question , text_question;

    private Button btn_choose1 , btn_choose2 , btn_choose3, btn_choose4, btn_choose5, btn_choose6;

    private int numberOfElegant, numberOfClassic, numberOfSexy, numberOfDramatic, numberOfFeminine, numberOfNatural = 0;

    private ImageView backImageView;

    private RetrofitService retrofitService;

    private LoadingAlert loadingAlert;

    private List<UserAnswerDTO> partStyleType;


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

        setContentView(R.layout.activity_fashion_style_quiz);
        retrofitService = new RetrofitService();
        loadingAlert = new LoadingAlert(FashionStyleQuizActivity.this);
        loadingAlert.startAlertDialog();

        number_question = findViewById(R.id.number_question);
        text_question = findViewById(R.id.text_question);

        btn_choose1 = findViewById(R.id.btn_choose1);
        btn_choose2 = findViewById(R.id.btn_choose2);
        btn_choose3 = findViewById(R.id.btn_choose3);
        btn_choose4 = findViewById(R.id.btn_choose4);
        btn_choose5 = findViewById(R.id.btn_choose5);
        btn_choose6 = findViewById(R.id.btn_choose6);
        backImageView = findViewById(R.id.image_back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FashionStyleQuizActivity.this, FashionStyleActivity.class);
                startActivity(intent);
            }
        });


        List<String> fashionStyleQuestions = new ArrayList<String>();
        List<Long> idFashionStyleQuestions = new ArrayList<Long>();
        List<List<String>> fashionStyleAnswers = new ArrayList<List<String>>();
        List<String> elegantResults = new ArrayList<String>();
        List<String> classicResults = new ArrayList<String>();
        List<String> sexyResults = new ArrayList<String>();
        List<String> dramaticResults = new ArrayList<String>();
        List<String> feminineResults = new ArrayList<String>();
        List<String> naturalResults = new ArrayList<String>();
        partStyleType= new ArrayList<UserAnswerDTO>();


        StyleQuestionService styleQuestionService =  retrofitService.createService(StyleQuestionService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        styleQuestionService.getAllStyleTypeQuestions().enqueue(new Callback<ResponseModel<List<StyleQuestion>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<StyleQuestion>, ErrorDTO>> call, Response<ResponseModel<List<StyleQuestion>, ErrorDTO>> response) {
                if(response.body() != null) {
                    List<StyleQuestion> questions = response.body().getResponse();


                    for (StyleQuestion question : questions) {

                        fashionStyleQuestions.add(question.getQuestion());
                        idFashionStyleQuestions.add(question.getId());

                        List<String> answersEachQuestion = new ArrayList<String>();
                        answersEachQuestion.add(question.getClassicOption());
                        answersEachQuestion.add(question.getDramaticOption());
                        answersEachQuestion.add(question.getElegantOption());
                        answersEachQuestion.add(question.getFeminineOption());
                        answersEachQuestion.add(question.getNaturalOption());
                        answersEachQuestion.add(question.getSexyOption());
                        fashionStyleAnswers.add(answersEachQuestion);

                        elegantResults.add(question.getElegantOption());
                        classicResults.add(question.getClassicOption());
                        sexyResults.add(question.getSexyOption());
                        dramaticResults.add(question.getDramaticOption());
                        feminineResults.add(question.getFeminineOption());
                        naturalResults.add(question.getNaturalOption());

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!fashionStyleQuestions.isEmpty()) {
                                loadingAlert.closeDialog();
                                showFashionStyleQuiz(idFashionStyleQuestions, fashionStyleQuestions, fashionStyleAnswers, elegantResults,
                                        classicResults, sexyResults, dramaticResults, feminineResults, naturalResults);
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ResponseModel<List<StyleQuestion>, ErrorDTO>> call, Throwable t) {

            }
        });

    }

    public void addPartFashionStylePart(Long questionId, String answer){
        UserAnswerDTO userAnswerDTO = new UserAnswerDTO();
        userAnswerDTO.setQuestionId(questionId);
        userAnswerDTO.setUserAnswer(answer);
        partStyleType.add(userAnswerDTO);

    }


    public void checkFashionStyleAnswer(int selectedAnswerIndex, List<Long> idFashionStyleQuestions, List<String> fashionStyleQuestions,List<List<String>> fashionStyleAnswers, List<String> elegantResults,
        List<String> classicResults, List<String> sexyResults, List<String> dramaticResults, List<String> feminineResults, List<String> naturalResults) {
        String selectedFashionStyleAnswer = fashionStyleAnswers.get(currentFashionStyleQuestionIndex).get(selectedAnswerIndex);
        String elegantResult = elegantResults.get(currentFashionStyleQuestionIndex);
        String classicResult = classicResults.get(currentFashionStyleQuestionIndex);
        String sexyResult = sexyResults.get(currentFashionStyleQuestionIndex);
        String dramaticResult = dramaticResults.get(currentFashionStyleQuestionIndex);
        String feminineResult = feminineResults.get(currentFashionStyleQuestionIndex);
        String naturalResult = naturalResults.get(currentFashionStyleQuestionIndex);
        addPartFashionStylePart(idFashionStyleQuestions.get(currentFashionStyleQuestionIndex),selectedFashionStyleAnswer);
        String result = "";

        if (selectedFashionStyleAnswer.equals(elegantResult)) {
            numberOfElegant += 1;
        }
        else if (selectedFashionStyleAnswer.equals(classicResult)){
            numberOfClassic += 1;
        }
        else if (selectedFashionStyleAnswer.equals(sexyResult)){
            numberOfSexy += 1;
        }
        else if (selectedFashionStyleAnswer.equals(dramaticResult)){
            numberOfDramatic += 1;
        }
        else if (selectedFashionStyleAnswer.equals(feminineResult)){
            numberOfFeminine += 1;
        }
        else if (selectedFashionStyleAnswer.equals(naturalResult)){
            numberOfNatural += 1;
        }

        // Move to the next question
        currentFashionStyleQuestionIndex++;

        if (currentFashionStyleQuestionIndex < fashionStyleQuestions.size()) {
            showQuestion(fashionStyleQuestions.size(), currentFashionStyleQuestionIndex, fashionStyleQuestions, fashionStyleAnswers);
        }
        else{
            int arrayAnswer[] = {numberOfElegant, numberOfClassic, numberOfSexy, numberOfDramatic, numberOfFeminine, numberOfNatural};
            int indexOfLargest = findIndexOfLargest(arrayAnswer);
            switch(indexOfLargest){
                case 0:
                    Toast.makeText(this, "Your style is elegant", Toast.LENGTH_SHORT).show();
                    result = "elegant";
                    break;
                case 1:
                    Toast.makeText(this, "Your style is classic", Toast.LENGTH_SHORT).show();
                    result = "classic";
                    break;
                case 2:
                    Toast.makeText(this, "Your style is sexy", Toast.LENGTH_SHORT).show();
                    result = "sexy";
                    break;
                case 3:
                    Toast.makeText(this, "Your style is dramatic", Toast.LENGTH_SHORT).show();
                    result = "dramatic";
                    break;
                case 4:
                    Toast.makeText(this, "Your style is feminine", Toast.LENGTH_SHORT).show();
                    result = "feminine";
                    break;
                case 5:
                    Toast.makeText(this, "Your style is natural", Toast.LENGTH_SHORT).show();
                    result = "natural";
                    break;

            }
            saveAttemptQuiz(result);
        }
    }

    public void saveAttemptQuiz(String result){
        SubmitStyleTypeQuizDTO submitStyleTypeQuizDTO = new SubmitStyleTypeQuizDTO();
        submitStyleTypeQuizDTO.setPartStyleType(partStyleType);
        submitStyleTypeQuizDTO.setResult(result);



        AttemptQuizService attemptQuizService = retrofitService.createService(AttemptQuizService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        attemptQuizService.submitAttemptStyleQuiz(submitStyleTypeQuizDTO).enqueue(new Callback<ResponseModel<AttemptQuiz, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<AttemptQuiz, ErrorDTO>> call, Response<ResponseModel<AttemptQuiz, ErrorDTO>> response) {
                if(response.body() != null) {
                    Intent intent = new Intent(FashionStyleQuizActivity.this, FashionStyleAboutActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<AttemptQuiz, ErrorDTO>> call, Throwable t) {

            }
        });
    }

    public int findIndexOfLargest(int array[]){
        int max = array[0];
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public void showFashionStyleQuiz(List<Long> idFashionStyleQuestions, List<String> fashionStyleQuestions, List<List<String>> fashionStyleAnswers, List<String> elegantResults,
                                     List<String> classicResults, List<String> sexyResults, List<String> dramaticResults, List<String> feminineResults, List<String> naturalResults){
        int totalNumberQuestion = fashionStyleQuestions.size();
        showQuestion(totalNumberQuestion, currentFashionStyleQuestionIndex, fashionStyleQuestions, fashionStyleAnswers);

        btn_choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkFashionStyleAnswer(0,idFashionStyleQuestions, fashionStyleQuestions,fashionStyleAnswers, elegantResults,
                        classicResults, sexyResults, dramaticResults, feminineResults, naturalResults);
            }
        });
        btn_choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkFashionStyleAnswer(1, idFashionStyleQuestions, fashionStyleQuestions,fashionStyleAnswers, elegantResults,
                        classicResults, sexyResults, dramaticResults, feminineResults, naturalResults);
            }
        });
        btn_choose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFashionStyleAnswer(2,idFashionStyleQuestions, fashionStyleQuestions,fashionStyleAnswers, elegantResults,
                        classicResults, sexyResults, dramaticResults, feminineResults, naturalResults);

            }
        });
        btn_choose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFashionStyleAnswer(3, idFashionStyleQuestions, fashionStyleQuestions,fashionStyleAnswers, elegantResults,
                        classicResults, sexyResults, dramaticResults, feminineResults, naturalResults);

            }
        });
        btn_choose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFashionStyleAnswer(4, idFashionStyleQuestions, fashionStyleQuestions,fashionStyleAnswers, elegantResults,
                        classicResults, sexyResults, dramaticResults, feminineResults, naturalResults);

            }
        });
        btn_choose6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFashionStyleAnswer(5, idFashionStyleQuestions, fashionStyleQuestions,fashionStyleAnswers, elegantResults,
                        classicResults, sexyResults, dramaticResults, feminineResults, naturalResults);

            }
        });

    }

    public void showAnswer(int numberOfAnswer, int questionIndex, List<List<String>> answers){
         if(numberOfAnswer == 6){
            btn_choose1.setVisibility(View.VISIBLE);
            btn_choose2.setVisibility(View.VISIBLE);
            btn_choose3.setVisibility(View.VISIBLE);
            btn_choose4.setVisibility(View.VISIBLE);
            btn_choose5.setVisibility(View.VISIBLE);
            btn_choose6.setVisibility(View.VISIBLE);
            btn_choose1.setText(String.valueOf(answers.get(questionIndex).get(0)));
            btn_choose2.setText(String.valueOf(answers.get(questionIndex).get(1)));
            btn_choose3.setText(String.valueOf(answers.get(questionIndex).get(2)));
            btn_choose4.setText(String.valueOf(answers.get(questionIndex).get(3)));
            btn_choose5.setText(String.valueOf(answers.get(questionIndex).get(4)));
            btn_choose6.setText(String.valueOf(answers.get(questionIndex).get(5)));
        }
    }

    public void showQuestion(int totalNumberQuestion, int questionIndex,List<String> questions, List<List<String>> answers){
        number_question.setText(String.valueOf("Question " + (questionIndex+1) + "/" + totalNumberQuestion));
        text_question.setText(String.valueOf(questions.get(questionIndex)));
        showAnswer(answers.get(questionIndex).size(), questionIndex, answers);
    }


}