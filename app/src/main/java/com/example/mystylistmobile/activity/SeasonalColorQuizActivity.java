package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.SeasonalColorQuestionDTO;
import com.example.mystylistmobile.dto.SubmitSeasonalColorQuizDTO;
import com.example.mystylistmobile.dto.UserAnswerDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.AttemptQuiz;
import com.example.mystylistmobile.model.ContrastQuestion;
import com.example.mystylistmobile.model.UndertoneQuestion;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.AttemptQuizService;
import com.example.mystylistmobile.service.ContrastQuestionService;
import com.example.mystylistmobile.service.SeasonalColorQuestionService;
import com.example.mystylistmobile.service.SeasonalColorService;
import com.example.mystylistmobile.service.UndertoneQuestionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeasonalColorQuizActivity extends AppCompatActivity {

    private int currentUndertoneQuestionIndex, currentContrastQuestionIndex = 0;
    private int numberOfCoolAnswer, numberOfWarmAnswer = 0;
    private int numberOfHighContrastAnswer, numberOfLowContrastAnswer = 0;
    private String userUndertone, userContrast = "";
    private boolean isNextPart = false;
    private TextView number_question , text_question;
    private Button btn_choose1 , btn_choose2 , btn_choose3, btn_choose4;

    private ImageView backImageView;

    private RetrofitService retrofitService;

    private LoadingAlert loadingAlert;

    private List<UserAnswerDTO> partUndertoneAnswer;

    private List<UserAnswerDTO> partContrastAnswer;

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

        setContentView(R.layout.activity_seasonal_color_quiz);
        retrofitService = new RetrofitService();
        loadingAlert = new LoadingAlert(SeasonalColorQuizActivity.this);
        loadingAlert.startAlertDialog();

        number_question = findViewById(R.id.number_question);
        text_question = findViewById(R.id.text_question);

        btn_choose1 = findViewById(R.id.btn_choose1);
        btn_choose2 = findViewById(R.id.btn_choose2);
        btn_choose3 = findViewById(R.id.btn_choose3);
        btn_choose4 = findViewById(R.id.btn_choose4);
        backImageView = findViewById(R.id.image_back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeasonalColorQuizActivity.this, SeasonalColorActivity.class);
                startActivity(intent);
            }
        });


        List<String> contrastQuestions = new ArrayList<String>();
        List<Long> idContrastQuestions = new ArrayList<Long>();
        List<List<String>> contrastAnswers = new ArrayList<List<String>>();
        partUndertoneAnswer = new ArrayList<UserAnswerDTO>();
        partContrastAnswer = new ArrayList<UserAnswerDTO>();


        List<String> highContrastResults = new ArrayList<String>();
        List<String> lowContrastResults = new ArrayList<String>();
        List<String> undertoneQuestions = new ArrayList<String>();
        List<Long> idUndertoneQuestions = new ArrayList<Long>();
        List<List<String>> undertoneAnswers = new ArrayList<List<String>>();
        List<String> coolResults = new ArrayList<String>();
        List<String> warmResults = new ArrayList<String>();

        SeasonalColorQuestionService seasonalColorQuestionService = retrofitService.createService(SeasonalColorQuestionService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        seasonalColorQuestionService.getAllSeasonalColorQuestions().enqueue(new Callback<ResponseModel<SeasonalColorQuestionDTO, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<SeasonalColorQuestionDTO, ErrorDTO>> call, Response<ResponseModel<SeasonalColorQuestionDTO, ErrorDTO>> response) {
                if(response.body() != null) {
                    List<UndertoneQuestion> questions = response.body().getResponse().getUndertoneQuestions();
                    for (UndertoneQuestion question : questions) {
                        undertoneQuestions.add(question.getQuestion());
                        idUndertoneQuestions.add(question.getId());

                        List<String> answerEachQuestion = new ArrayList<String>();
                        answerEachQuestion.add(question.getCoolOption());
                        answerEachQuestion.add(question.getWarmOption());
                        answerEachQuestion.add(question.getNeutralOption());
                        undertoneAnswers.add(answerEachQuestion);

                        coolResults.add(question.getCoolOption());
                        warmResults.add(question.getWarmOption());

                    }
                    List<ContrastQuestion> questions2 = response.body().getResponse().getContrastQuestion();
                    for (ContrastQuestion question : questions2) {
                        contrastQuestions.add(question.getQuestion());
                        idContrastQuestions.add(question.getId());

                        List<String> answerEachQuestion = new ArrayList<String>();
                        answerEachQuestion.add(question.getHighOption());
                        answerEachQuestion.add(question.getLowOption());
                        answerEachQuestion.add(question.getMediumOption());
                        contrastAnswers.add(answerEachQuestion);

                        highContrastResults.add(question.getHighOption());
                        lowContrastResults.add(question.getLowOption());
                     }
                if (!undertoneQuestions.isEmpty() && !contrastQuestions.isEmpty()) {
                    loadingAlert.closeDialog();
                    showUndertoneQuiz(idUndertoneQuestions, undertoneQuestions, idContrastQuestions, contrastQuestions, undertoneAnswers,
                            contrastAnswers, coolResults, warmResults, highContrastResults, lowContrastResults);
                }
             }
            }
            @Override
            public void onFailure(Call<ResponseModel<SeasonalColorQuestionDTO, ErrorDTO>> call, Throwable t) {
                loadingAlert.closeDialog();
                t.printStackTrace();
                showToast("Error load seasonal color question");
            }
        });

       /* UndertoneQuestionService undertoneQuestionService = retrofitService.createService(UndertoneQuestionService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        undertoneQuestionService.getAllUndertoneQuestions().enqueue(new Callback<ResponseModel<List<UndertoneQuestion>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UndertoneQuestion>, ErrorDTO>> call, Response<ResponseModel<List<UndertoneQuestion>, ErrorDTO>> response) {
                if(response.body() != null) {
                    List<UndertoneQuestion> questions = response.body().getResponse();
                    for (UndertoneQuestion question : questions) {
                        undertoneQuestions.add(question.getQuestion());
                        idUndertoneQuestions.add(question.getId());

                        List<String> answerEachQuestion = new ArrayList<String>();
                        answerEachQuestion.add(question.getCoolOption());
                        answerEachQuestion.add(question.getWarmOption());
                        answerEachQuestion.add(question.getNeutralOption());
                        undertoneAnswers.add(answerEachQuestion);

                        coolResults.add(question.getCoolOption());
                        warmResults.add(question.getWarmOption());

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UndertoneQuestion>, ErrorDTO>> call, Throwable t) {
                t.printStackTrace();
                showToast("Error load undertone question");
            }
        });



        ContrastQuestionService contrastQuestionService = retrofitService.createService(ContrastQuestionService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        contrastQuestionService.getAllContrastQuestions().enqueue(new Callback<ResponseModel<List<ContrastQuestion>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<ContrastQuestion>, ErrorDTO>> call, Response<ResponseModel<List<ContrastQuestion>, ErrorDTO>> response) {
                if(response.body() != null) {
                    List<ContrastQuestion> questions2 = response.body().getResponse();
                    for (ContrastQuestion question : questions2) {
                        contrastQuestions.add(question.getQuestion());
                        idContrastQuestions.add(question.getId());

                        List<String> answerEachQuestion = new ArrayList<String>();
                        answerEachQuestion.add(question.getHighOption());
                        answerEachQuestion.add(question.getLowOption());
                        answerEachQuestion.add(question.getMediumOption());
                        contrastAnswers.add(answerEachQuestion);

                        highContrastResults.add(question.getHighOption());
                        lowContrastResults.add(question.getLowOption());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!undertoneQuestions.isEmpty() && !contrastQuestions.isEmpty()) {
                                loadingAlert.closeDialog();
                                showUndertoneQuiz(idUndertoneQuestions, undertoneQuestions, idContrastQuestions, contrastQuestions, undertoneAnswers,
                                        contrastAnswers, coolResults, warmResults, highContrastResults, lowContrastResults);
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<ContrastQuestion>, ErrorDTO>> call, Throwable t) {
                t.printStackTrace();
                showToast("Error load contrast question");
            }
        });*/


    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void addPartUndertoneAnswer(Long questionId, String userAnswer){
        UserAnswerDTO userAnswerDTO = new UserAnswerDTO();
        userAnswerDTO.setQuestionId(questionId);
        userAnswerDTO.setUserAnswer(userAnswer);
        partUndertoneAnswer.add(userAnswerDTO);
    }

    public void addPartContrastAnswer(Long questionId, String userAnswer){
        UserAnswerDTO userAnswerDTO = new UserAnswerDTO();
        userAnswerDTO.setQuestionId(questionId);
        userAnswerDTO.setUserAnswer(userAnswer);
        partContrastAnswer.add(userAnswerDTO);
    }

    public void showUndertoneQuiz(List<Long> idUndertoneQuestions, List<String> undertoneQuestions, List<Long> idContrastQuestions, List<String> contrastQuestions, List<List<String>> undertoneAnswers,
                                  List<List<String>> contrastAnswers, List<String> coolResults, List<String> warmResults, List<String> highContrastResults, List<String> lowContrastResults){
        int totalNumberQuestion = undertoneQuestions.size() + contrastQuestions.size();
        this.showQuestion(totalNumberQuestion, currentUndertoneQuestionIndex, undertoneQuestions, undertoneAnswers);

        btn_choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNextPart  == false){
                    checkUnderToneAnswer(0, idUndertoneQuestions, undertoneQuestions, undertoneAnswers, contrastQuestions, contrastAnswers, coolResults, warmResults);}
                else{
                    checkContrastAnswer(0, undertoneQuestions, idContrastQuestions, contrastQuestions, contrastAnswers, highContrastResults, lowContrastResults);
                }
            }
        });
        btn_choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNextPart  == false){
                    checkUnderToneAnswer(1, idUndertoneQuestions, undertoneQuestions, undertoneAnswers, contrastQuestions, contrastAnswers, coolResults, warmResults);
                }
                else{
                    checkContrastAnswer(1, undertoneQuestions, idContrastQuestions, contrastQuestions, contrastAnswers, highContrastResults, lowContrastResults);
                }
            }
        });
        btn_choose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNextPart  == false){
                    checkUnderToneAnswer(2,idUndertoneQuestions, undertoneQuestions, undertoneAnswers, contrastQuestions, contrastAnswers, coolResults, warmResults);}
                else{
                    checkContrastAnswer(2, undertoneQuestions, idContrastQuestions, contrastQuestions, contrastAnswers, highContrastResults, lowContrastResults);
                }

            }
        });
    }
    public void validateSeasonalColor(){
        String result = "";
        if(userUndertone == "Cool"){
            if(userContrast == "High"){
                Toast.makeText(this, "You are winter", Toast.LENGTH_SHORT).show();
                result = "winter";
            }
            else if(userContrast == "Low"){
                Toast.makeText(this, "You are summer", Toast.LENGTH_SHORT).show();
                result = "summer";
            }
        }
        else if(userUndertone == "Warm"){
            if(userContrast == "High"){
                Toast.makeText(this, "You are autumn", Toast.LENGTH_SHORT).show();
                result = "autumn";
            }
            else if(userContrast == "Low"){
                Toast.makeText(this, "You are spring", Toast.LENGTH_SHORT).show();
                result = "spring";
            }
        }
        else{
            Toast.makeText(this, "You are neutral", Toast.LENGTH_SHORT).show();
            result = "neutral";
        }
        saveAttemptQuiz(result);

    }
    public void saveAttemptQuiz(String result){
        SubmitSeasonalColorQuizDTO seasonalColorQuizDTO = new SubmitSeasonalColorQuizDTO();
        seasonalColorQuizDTO.setPartContrast(partContrastAnswer);
        seasonalColorQuizDTO.setPartUndertone(partUndertoneAnswer);
        seasonalColorQuizDTO.setResult(result);


        AttemptQuizService attemptQuizService = retrofitService.createService(AttemptQuizService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        attemptQuizService.submitAttemptSeasonalColorQuiz(seasonalColorQuizDTO).enqueue(new Callback<ResponseModel<AttemptQuiz, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<AttemptQuiz, ErrorDTO>> call, Response<ResponseModel<AttemptQuiz, ErrorDTO>> response) {
                if(response.body() != null) {
                    Intent intent = new Intent(SeasonalColorQuizActivity.this, SeasonalColorAboutActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<AttemptQuiz, ErrorDTO>> call, Throwable t) {

            }
        });
    }

    public void checkContrastAnswer(int selectedAnswerIndex, List<String> undertoneQuestions, List<Long> idContrastQuestions, List<String> contrastQuestions, List<List<String>> contrastAnswers, List<String> highContrastResults, List<String> lowContrastResults){
        String selectedContrastAnswer = contrastAnswers.get(currentContrastQuestionIndex).get(selectedAnswerIndex);
        String highContrastResult = highContrastResults.get(currentContrastQuestionIndex);
        String lowContrastResult = lowContrastResults.get(currentContrastQuestionIndex);
        addPartContrastAnswer(idContrastQuestions.get(currentContrastQuestionIndex),selectedContrastAnswer);

        if (selectedContrastAnswer.equals(highContrastResult)) {
            numberOfHighContrastAnswer += 1;
        }
        else if (selectedContrastAnswer.equals(lowContrastResult)){
            numberOfLowContrastAnswer += 1;
        }
        // Move to the next question
        currentContrastQuestionIndex++;

        if (currentContrastQuestionIndex < contrastQuestions.size()) {
            int totalNumberQuestion = undertoneQuestions.size() + contrastQuestions.size();
            showQuestion(totalNumberQuestion, currentContrastQuestionIndex , contrastQuestions, contrastAnswers);
        }
        else{
            if( numberOfHighContrastAnswer >  numberOfLowContrastAnswer){
                userContrast = "High";
            }
            else if(numberOfLowContrastAnswer > numberOfHighContrastAnswer){
                userContrast = "Low";
            }
            else{
                userContrast = "Neutral";
            }
            validateSeasonalColor();
        }

    }

    public void checkUnderToneAnswer(int selectedAnswerIndex, List<Long> idUndertoneQuestions, List<String> undertoneQuestions, List<List<String>> undertoneAnswers, List<String> contrastQuestions, List<List<String>> contrastAnswers, List<String> coolResults, List<String> warmResults) {
        String selectedUndertoneAnswer = undertoneAnswers.get(currentUndertoneQuestionIndex).get(selectedAnswerIndex);
        String coolResult = coolResults.get(currentUndertoneQuestionIndex);
        String warmResult = warmResults.get(currentUndertoneQuestionIndex);
        addPartUndertoneAnswer(idUndertoneQuestions.get(currentUndertoneQuestionIndex),selectedUndertoneAnswer);
        if (selectedUndertoneAnswer.equals(coolResult)) {
            numberOfCoolAnswer += 1;
            // Pass question 2nd, double score
            if(currentUndertoneQuestionIndex == 2){
                numberOfCoolAnswer += 1;
            }
        }
        else if (selectedUndertoneAnswer.equals(warmResult)){
            numberOfWarmAnswer += 1;
            // Pass question 2nd, double score
            if(currentUndertoneQuestionIndex == 2){
                numberOfWarmAnswer += 1;
            }
        }


        // Move to the next question
        currentUndertoneQuestionIndex++;

        if (currentUndertoneQuestionIndex < undertoneQuestions.size()) {
            int totalNumberQuestion = undertoneQuestions.size() + contrastQuestions.size();
            showQuestion(totalNumberQuestion, currentUndertoneQuestionIndex, undertoneQuestions, undertoneAnswers);
        }
        else{
            if( numberOfCoolAnswer >= 3 || numberOfCoolAnswer > numberOfWarmAnswer){
                userUndertone = "Cool";
            }
            else if(numberOfWarmAnswer >= 3 || numberOfWarmAnswer > numberOfCoolAnswer){
                userUndertone = "Warm";
            }
            else{
                userUndertone = "Neutral";
            }
            isNextPart = true;

            int totalNumberQuestion = undertoneQuestions.size() + contrastQuestions.size();
            showQuestion(totalNumberQuestion, currentContrastQuestionIndex, contrastQuestions, contrastAnswers);
        }

    }

    public void showAnswer(int numberOfAnswer, int questionIndex, List<List<String>> answers){
        if (numberOfAnswer == 2){
            btn_choose1.setVisibility(View.VISIBLE);
            btn_choose2.setVisibility(View.VISIBLE);
            btn_choose3.setVisibility(View.GONE);
            btn_choose4.setVisibility(View.GONE);
            btn_choose1.setText(String.valueOf(answers.get(questionIndex).get(0)));
            btn_choose2.setText(String.valueOf(answers.get(questionIndex).get(1)));
        }
        else if(numberOfAnswer == 3){
            btn_choose1.setVisibility(View.VISIBLE);
            btn_choose2.setVisibility(View.VISIBLE);
            btn_choose3.setVisibility(View.VISIBLE);
            btn_choose4.setVisibility(View.GONE);
            btn_choose1.setText(String.valueOf(answers.get(questionIndex).get(0)));
            btn_choose2.setText(String.valueOf(answers.get(questionIndex).get(1)));
            btn_choose3.setText(String.valueOf(answers.get(questionIndex).get(2)));
        }
        else if(numberOfAnswer == 4){
            btn_choose1.setVisibility(View.VISIBLE);
            btn_choose2.setVisibility(View.VISIBLE);
            btn_choose3.setVisibility(View.VISIBLE);
            btn_choose4.setVisibility(View.VISIBLE);
            btn_choose1.setText(String.valueOf(answers.get(questionIndex).get(0)));
            btn_choose2.setText(String.valueOf(answers.get(questionIndex).get(1)));
            btn_choose3.setText(String.valueOf(answers.get(questionIndex).get(2)));
            btn_choose4.setText(String.valueOf(answers.get(questionIndex).get(3)));
        }
    }

    public int getNumberOfAnswers(List<String> answersEachQuestion){
        int numberOfAnswers = 0;
        for(String answer: answersEachQuestion){
            if(answer != null) {
                numberOfAnswers += 1;
            }
        }
        return numberOfAnswers;
    }

    public void showQuestion(int totalNumberQuestion, int questionIndex,List<String> questions, List<List<String>> answers){
       if(isNextPart == true){
           number_question.setText(String.valueOf("Question " + (totalNumberQuestion - questions.size() + questionIndex+1) + "/" + totalNumberQuestion));
       }
       else {
           number_question.setText(String.valueOf("Question " + (questionIndex+1) + "/" + totalNumberQuestion));
       }
        text_question.setText(String.valueOf(questions.get(questionIndex)));
        int numberOfAnswer = getNumberOfAnswers(answers.get(questionIndex));
        showAnswer(numberOfAnswer, questionIndex, answers);
    }

}