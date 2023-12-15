package com.example.mystylistmobile.dto;

import java.util.List;

public class SubmitStyleTypeQuizDTO {
    private List<UserAnswerDTO> partStyleType;
    private String result;

    public SubmitStyleTypeQuizDTO() {
    }

    public SubmitStyleTypeQuizDTO(List<UserAnswerDTO> partStyleType, String result) {
        this.partStyleType = partStyleType;
        this.result = result;
    }

    public List<UserAnswerDTO> getPartStyleType() {
        return partStyleType;
    }

    public void setPartStyleType(List<UserAnswerDTO> partStyleType) {
        this.partStyleType = partStyleType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
