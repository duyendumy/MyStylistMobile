package com.example.mystylistmobile.dto;

public class UserAnswerDTO {
    private Long questionId;
    private String userAnswer;

    public UserAnswerDTO() {
    }

    public UserAnswerDTO(Long questionId, String userAnswer) {
        this.questionId = questionId;
        this.userAnswer = userAnswer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
