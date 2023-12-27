package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.SeasonalColorQuestionDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;



import retrofit2.Call;
import retrofit2.http.GET;

public interface SeasonalColorQuestionService {
    @GET("/my-stylist/seasonal-color-question")
    Call<ResponseModel<SeasonalColorQuestionDTO, ErrorDTO>> getAllSeasonalColorQuestions();
}
