package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.AddStyleQuestionDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.model.StyleQuestion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface StyleQuestionService {

    @GET("/my-stylist/style-type-question")
    Call<ResponseModel<List<StyleQuestion>, ErrorDTO>> getAllStyleTypeQuestions();

    @POST("/my-stylist/style-type-question")
    Call<ResponseModel<StyleQuestion, ErrorDTO>> addStyleTypeQuestion(@Body AddStyleQuestionDTO styleQuestionDTO);
}
