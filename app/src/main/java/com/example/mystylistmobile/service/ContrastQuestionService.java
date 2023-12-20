package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.AddContrastQuestionDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.model.ContrastQuestion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ContrastQuestionService {
    @GET("/my-stylist/contrast-question")
    Call<ResponseModel<List<ContrastQuestion>, ErrorDTO>> getAllContrastQuestions();

    @POST("/my-stylist/contrast-question")
    Call<ResponseModel<ContrastQuestion, ErrorDTO>> addContrastQuestion(@Body AddContrastQuestionDTO contrastQuestion);
}
