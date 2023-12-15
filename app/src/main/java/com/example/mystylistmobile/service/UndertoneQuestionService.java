package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.model.StyleQuestion;
import com.example.mystylistmobile.model.UndertoneQuestion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UndertoneQuestionService {

    @GET("/my-stylist/undertone-question")
    Call<ResponseModel<List<UndertoneQuestion>, ErrorDTO>> getAllUndertoneQuestions();

    @POST("/my-stylist/undertone-question")
    Call<ResponseModel<UndertoneQuestion, ErrorDTO>> getAllUndertoneQuestions(@Body UndertoneQuestion undertoneQuestion);
}
