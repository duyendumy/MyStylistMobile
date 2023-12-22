package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.SubmitSeasonalColorQuizDTO;
import com.example.mystylistmobile.dto.SubmitStyleTypeQuizDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.model.AttemptQuiz;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AttemptQuizService {
    @POST("/my-stylist/attempt-quiz/seasonal-color")
    Call<ResponseModel<AttemptQuiz, ErrorDTO>> submitAttemptSeasonalColorQuiz(@Body SubmitSeasonalColorQuizDTO seasonalColorQuizDTO);

    @POST("/my-stylist/attempt-quiz/style-type")
    Call<ResponseModel<AttemptQuiz, ErrorDTO>> submitAttemptStyleQuiz(@Body SubmitStyleTypeQuizDTO styleTypeQuizDTO);
}
