package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.response.BodyShapeResponseDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BodyShapeService {
    @GET("/my-stylist/body-shapes/{bodyShapeId}")
    Call<ResponseModel<BodyShapeResponseDTO, ErrorDTO>> getBodyShapeById(@Path("bodyShapeId") Long bodyShapeId);
}
