package com.example.mystylistmobile.model;

public class StyleQuestion {

    private Long id;

    private String question;

    private String classicOption;

    private String naturalOption;

    private String sexyOption;

    private String dramaticOption;

    private String feminineOption;

    private String elegantOption;

    public StyleQuestion(Long id, String question, String classicOption, String naturalOption, String sexyOption, String dramaticOption, String feminineOption, String elegantOption) {
        this.id = id;
        this.question = question;
        this.classicOption = classicOption;
        this.naturalOption = naturalOption;
        this.sexyOption = sexyOption;
        this.dramaticOption = dramaticOption;
        this.feminineOption = feminineOption;
        this.elegantOption = elegantOption;
    }

    public StyleQuestion() {
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

    public String getClassicOption() {
        return classicOption;
    }

    public void setClassicOption(String classicOption) {
        this.classicOption = classicOption;
    }

    public String getNaturalOption() {
        return naturalOption;
    }

    public void setNaturalOption(String naturalOption) {
        this.naturalOption = naturalOption;
    }

    public String getSexyOption() {
        return sexyOption;
    }

    public void setSexyOption(String sexyOption) {
        this.sexyOption = sexyOption;
    }

    public String getDramaticOption() {
        return dramaticOption;
    }

    public void setDramaticOption(String dramaticOption) {
        this.dramaticOption = dramaticOption;
    }

    public String getFeminineOption() {
        return feminineOption;
    }

    public void setFeminineOption(String feminineOption) {
        this.feminineOption = feminineOption;
    }

    public String getElegantOption() {
        return elegantOption;
    }

    public void setElegantOption(String elegantOption) {
        this.elegantOption = elegantOption;
    }
}
