package com.example.mystylistmobile.dto;

import java.util.List;

public class SubmitSeasonalColorQuizDTO {
    private List<UserAnswerDTO> partUndertone;
    private List<UserAnswerDTO> partContrast;
    private String result;

    public SubmitSeasonalColorQuizDTO() {
    }

    public SubmitSeasonalColorQuizDTO(List<UserAnswerDTO> partUndertone, List<UserAnswerDTO> partContrast, String result) {
        this.partUndertone = partUndertone;
        this.partContrast = partContrast;
        this.result = result;
    }

    public List<UserAnswerDTO> getPartUndertone() {
        return partUndertone;
    }

    public void setPartUndertone(List<UserAnswerDTO> partUndertone) {
        this.partUndertone = partUndertone;
    }

    public List<UserAnswerDTO> getPartContrast() {
        return partContrast;
    }

    public void setPartContrast(List<UserAnswerDTO> partContrast) {
        this.partContrast = partContrast;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
