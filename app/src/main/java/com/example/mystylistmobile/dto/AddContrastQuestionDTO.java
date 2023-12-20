package com.example.mystylistmobile.dto;

public class AddContrastQuestionDTO {

    private String question;
    private String highOption;
    private String lowOption;
    private String mediumOption;

    public AddContrastQuestionDTO() {
    }

    public AddContrastQuestionDTO(String question, String highOption, String lowOption, String mediumOption) {
        this.question = question;
        this.highOption = highOption;
        this.lowOption = lowOption;
        this.mediumOption = mediumOption;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHighOption() {
        return highOption;
    }

    public void setHighOption(String highOption) {
        this.highOption = highOption;
    }

    public String getLowOption() {
        return lowOption;
    }

    public void setLowOption(String lowOption) {
        this.lowOption = lowOption;
    }

    public String getMediumOption() {
        return mediumOption;
    }

    public void setMediumOption(String mediumOption) {
        this.mediumOption = mediumOption;
    }
}
