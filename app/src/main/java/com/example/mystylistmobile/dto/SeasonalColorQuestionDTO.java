package com.example.mystylistmobile.dto;

import com.example.mystylistmobile.model.ContrastQuestion;
import com.example.mystylistmobile.model.UndertoneQuestion;

import java.util.List;

public class SeasonalColorQuestionDTO {
    private List<ContrastQuestion> contrastQuestion;
    private List<UndertoneQuestion> undertoneQuestions;

    public SeasonalColorQuestionDTO() {
    }

    public SeasonalColorQuestionDTO(List<ContrastQuestion> contrastQuestion, List<UndertoneQuestion> undertoneQuestions) {
        this.contrastQuestion = contrastQuestion;
        this.undertoneQuestions = undertoneQuestions;
    }

    public List<ContrastQuestion> getContrastQuestion() {
        return contrastQuestion;
    }

    public void setContrastQuestion(List<ContrastQuestion> contrastQuestion) {
        this.contrastQuestion = contrastQuestion;
    }

    public List<UndertoneQuestion> getUndertoneQuestions() {
        return undertoneQuestions;
    }

    public void setUndertoneQuestions(List<UndertoneQuestion> undertoneQuestions) {
        this.undertoneQuestions = undertoneQuestions;
    }
}
