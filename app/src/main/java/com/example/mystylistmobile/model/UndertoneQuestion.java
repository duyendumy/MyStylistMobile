package com.example.mystylistmobile.model;

public class UndertoneQuestion {

    private Long id;

    private String question;

    private String coolOption;

    private String warmOption;

    private String neutralOption;

    public UndertoneQuestion(Long id, String question, String coolOption, String warmOption, String neutralOption) {
        this.id = id;
        this.question = question;
        this.coolOption = coolOption;
        this.warmOption = warmOption;
        this.neutralOption = neutralOption;
    }

    public UndertoneQuestion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
