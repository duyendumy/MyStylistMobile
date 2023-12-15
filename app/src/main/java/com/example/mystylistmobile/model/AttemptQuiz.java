package com.example.mystylistmobile.model;

import java.time.Instant;
import java.util.List;

public class AttemptQuiz {
    private Long id;
    private String completedDate;
    private String quizType;
    private String result;

    public AttemptQuiz() {
    }

    public AttemptQuiz(Long id, String completedDate, String quizType, String result) {
        this.id = id;
        this.completedDate = completedDate;
        this.quizType = quizType;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
