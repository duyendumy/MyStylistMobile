package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystylistmobile.R;

public class TempQuizActivity extends AppCompatActivity {

    private String[] undertoneQuestions = {
            "Check the color or your wrist blood vessels.",
            "Do you look better in gold or silver clothing?",
            "What is your eye color?",
            "What is your hair color?",
    };
    private String[][] undertoneAnswers = {
            {"Cool (Purple - Blue)","Warm (Green)","Neutral (Purple - Green)"},
            {"Cool (Silver )","Warm (Gold )","Neutral (Both)"},
            {"Grey, Blue, Black","Brown, Amber, Hazel"},
            {"Black, Grey, Ashy brown","Red, Brown, Caramel"},
    };
    private String[] coolResults = {"Cool (Purple - Blue)","Cool (Silver )","Grey, Blue, Black","Black, Grey, Ashy brown"};
    private String[] warmResults = {"Warm (Green)","Warm (Gold )","Brown, Amber, Hazel","Red, Brown, Caramel"};

    private String[] contrastQuestions = {
            "Choose your best answer"
    };
    private String[][] contrastAnswers = {
            {"My hair is significantly darker than my skin tone","There is not a big difference between the color of my hair and my skin tone", "Another answer"}
    };
    private String[] highContrastResults = {"My hair is significantly darker than my skin tone"};
    private String[] lowContrastResults = {"There is not a big difference between the color of my hair and my skin tone"};

    private String[] typeShapeQuestions = {
            "Which Body Type best characterizes your shape?"
    };
    private String[][] typeShapeAnswers = {
            {"Hourglass","Triangle","Inverted triangle", "Rectangle"}
    };
    private int currentUndertoneQuestionIndex = 0;
    private int currentContrastQuestionIndex = 0;
    private int currentBodyTypeQuestionIndex = 0;
    private int numberOfCoolAnswer = 0;
    private int numberOfWarmAnswer = 0;
    private int numberOfHighContrastAnswer = 0;
    private int numberOfLowContrastAnswer = 0;
    private String userUndertone = "";
    private String userContrast = "";
    private String userBodyType = "";
    private boolean isNextPart = false;
    private TextView number_question , text_question;
    private Button btn_choose1 , btn_choose2 , btn_choose3, btn_choose4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_quiz);
        number_question = findViewById(R.id.number_question);
        text_question = findViewById(R.id.text_question);

        btn_choose1 = findViewById(R.id.btn_choose1);
        btn_choose2 = findViewById(R.id.btn_choose2);
        btn_choose3 = findViewById(R.id.btn_choose3);
        btn_choose4 = findViewById(R.id.btn_choose4);

        findViewById(R.id.image_back).setOnClickListener(
                a-> finish()
        );
       showUndertoneQuiz();
    }

    public void showBodyTypeQuiz(){
        int totalNumberQuestion = typeShapeQuestions.length;
        this.showQuestion(totalNumberQuestion, currentBodyTypeQuestionIndex, typeShapeQuestions, typeShapeAnswers);
        btn_choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBodyTypeAnswer(0);
            }
        });
        btn_choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBodyTypeAnswer(1);
            }
        });
        btn_choose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBodyTypeAnswer(2);

            }
        });
        btn_choose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBodyTypeAnswer(3);

            }
        });

    }

    public void showUndertoneQuiz(){
        int totalNumberQuestion = undertoneQuestions.length + contrastQuestions.length;
        this.showQuestion(totalNumberQuestion, currentUndertoneQuestionIndex, undertoneQuestions, undertoneAnswers);

        btn_choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNextPart  == false){
                    checkUnderToneAnswer(0);}
                else{
                    checkContrastAnswer(0);}
            }
        });
        btn_choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNextPart  == false){
                    checkUnderToneAnswer(1);}
                else{
                    checkContrastAnswer(1);}
            }
        });
        btn_choose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNextPart  == false){
                    checkUnderToneAnswer(2);}
                else{
                    checkContrastAnswer(2);}

            }
        });
    }
    public void validateSeasonalColor(){
        if(userUndertone == "Cool"){
            if(userContrast == "High"){
                Toast.makeText(this, "You are winter", Toast.LENGTH_SHORT).show();
            }
            else if(userContrast == "Low"){
                Toast.makeText(this, "You are summer", Toast.LENGTH_SHORT).show();
            }
        }
        else if(userUndertone == "Warm"){
            if(userContrast == "High"){
                Toast.makeText(this, "You are autumn", Toast.LENGTH_SHORT).show();
            }
            else if(userContrast == "Low"){
                Toast.makeText(this, "You are spring", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "You are neutral", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkBodyTypeAnswer(int selectedAnswerIndex){
        String selectedBodyTypeAnswer = typeShapeAnswers[currentBodyTypeQuestionIndex][selectedAnswerIndex];
        if (selectedBodyTypeAnswer.equals("Hourglass")) {
            userBodyType = "Hourglass";
        }
        else if (selectedBodyTypeAnswer.equals("Triangle")){
            userBodyType = "Triangle";
        }
        else if (selectedBodyTypeAnswer.equals("Inverted triangle")){
            userBodyType = "Inverted triangle";
        }
        else if (selectedBodyTypeAnswer.equals("Rectangle")){
            userBodyType = "Rectangle";
        }
        // Move to the next question
        currentBodyTypeQuestionIndex++;

        if (currentBodyTypeQuestionIndex < typeShapeQuestions.length) {
            //    showBodyTypeQuestion(currentBodyTypeQuestionIndex);
            int totalNumberQuestion = typeShapeQuestions.length;
            this.showQuestion(totalNumberQuestion, currentBodyTypeQuestionIndex, typeShapeQuestions, typeShapeAnswers);
        }
        else{
            Toast.makeText(this, "You are " + userBodyType, Toast.LENGTH_SHORT).show();
        }
    }
    public void checkContrastAnswer(int selectedAnswerIndex){
        String selectedContrastAnswer = contrastAnswers[currentContrastQuestionIndex][selectedAnswerIndex];
        String highContrastResult = highContrastResults[currentContrastQuestionIndex];
        String lowContrastResult = lowContrastResults[currentContrastQuestionIndex];

        if (selectedContrastAnswer.equals(highContrastResult)) {
            numberOfHighContrastAnswer += 1;
        }
        else if (selectedContrastAnswer.equals(lowContrastResult)){
            numberOfLowContrastAnswer += 1;
        }
        // Move to the next question
        currentContrastQuestionIndex++;

        if (currentContrastQuestionIndex < contrastQuestions.length) {
            //  showContrastQuestion(currentContrastQuestionIndex);
            int totalNumberQuestion = undertoneQuestions.length + contrastQuestions.length;
            this.showQuestion(totalNumberQuestion, currentContrastQuestionIndex, contrastQuestions, contrastAnswers);
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

    public void checkUnderToneAnswer(int selectedAnswerIndex) {
        String selectedUndertoneAnswer = undertoneAnswers[currentUndertoneQuestionIndex][selectedAnswerIndex];
        String coolResult = coolResults[currentUndertoneQuestionIndex];
        String warmResult = warmResults[currentUndertoneQuestionIndex];

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

        if (currentUndertoneQuestionIndex < undertoneQuestions.length) {
            // showUndertoneQuestion(currentUndertoneQuestionIndex);
            int totalNumberQuestion = undertoneQuestions.length + contrastQuestions.length;
            this.showQuestion(totalNumberQuestion, currentUndertoneQuestionIndex, undertoneQuestions, undertoneAnswers);
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
            //    showContrastQuestion(currentContrastQuestionIndex);
            int totalNumberQuestion = undertoneQuestions.length + contrastQuestions.length;
            this.showQuestion(totalNumberQuestion, currentContrastQuestionIndex, contrastQuestions, contrastAnswers);
        }
    }

    public void showAnswer(int numberOfAnswer, int questionIndex, String [][] answers){
        if (numberOfAnswer == 2){
            btn_choose1.setVisibility(View.VISIBLE);
            btn_choose2.setVisibility(View.VISIBLE);
            btn_choose3.setVisibility(View.GONE);
            btn_choose4.setVisibility(View.GONE);
            btn_choose1.setText(String.valueOf(answers[questionIndex][0]));
            btn_choose2.setText(String.valueOf(answers[questionIndex][1]));
        }
        else if(numberOfAnswer == 3){
            btn_choose1.setVisibility(View.VISIBLE);
            btn_choose2.setVisibility(View.VISIBLE);
            btn_choose3.setVisibility(View.VISIBLE);
            btn_choose4.setVisibility(View.GONE);
            btn_choose1.setText(String.valueOf(answers[questionIndex][0]));
            btn_choose2.setText(String.valueOf(answers[questionIndex][1]));
            btn_choose3.setText(String.valueOf(answers[questionIndex][2]));
        }
        else if(numberOfAnswer == 4){
            btn_choose1.setVisibility(View.VISIBLE);
            btn_choose2.setVisibility(View.VISIBLE);
            btn_choose3.setVisibility(View.VISIBLE);
            btn_choose4.setVisibility(View.VISIBLE);
            btn_choose1.setText(String.valueOf(answers[questionIndex][0]));
            btn_choose2.setText(String.valueOf(answers[questionIndex][1]));
            btn_choose3.setText(String.valueOf(answers[questionIndex][2]));
            btn_choose4.setText(String.valueOf(answers[questionIndex][3]));
        }
    }

    public void showQuestion(int totalNumberQuestion, int questionIndex,String[] questions, String[][] answers){
        number_question.setText(String.valueOf("Question " + (questionIndex+1) + "/" + totalNumberQuestion));
        text_question.setText(String.valueOf(questions[questionIndex]));
        showAnswer(answers[questionIndex].length, questionIndex, answers);
    }

}