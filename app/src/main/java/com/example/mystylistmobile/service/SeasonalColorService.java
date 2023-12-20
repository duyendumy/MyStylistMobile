package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.SeasonalColorResponseDTO;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface SeasonalColorService {

    @GET("/my-stylist/seasonal-colors/{seasonalColorId}")
    Call<ResponseModel<SeasonalColorResponseDTO, ErrorDTO>> getSeasonalColorById(@Path("seasonalColorId") Long seasonalColorId);

    @GET("/my-stylist/seasonal-colors")
    Call<ResponseModel<List<SeasonalColorResponseDTO>, ErrorDTO>> getAllSeasonalColors();
}

