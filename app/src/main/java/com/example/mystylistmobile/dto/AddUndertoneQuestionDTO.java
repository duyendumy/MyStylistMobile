package com.example.mystylistmobile.dto;

public class AddUndertoneQuestionDTO {

    private String question;

    private String coolOption;

    private String warmOption;

    private String neutralOption;

    public AddUndertoneQuestionDTO() {
    }

    public AddUndertoneQuestionDTO(String question, String coolOption, String warmOption, String neutralOption) {
        this.question = question;
        this.coolOption = coolOption;
        this.warmOption = warmOption;
        this.neutralOption = neutralOption;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCoolOption() {
        return coolOption;
    }

    public void setCoolOption(String coolOption) {
        this.coolOption = coolOption;
    }

    public String getWarmOption() {
        return warmOption;
    }

    public void setWarmOption(String warmOption) {
        this.warmOption = warmOption;
    }

    public String getNeutralOption() {
        return neutralOption;
    }

    public void setNeutralOption(String neutralOption) {
        this.neutralOption = neutralOption;
    }
}
