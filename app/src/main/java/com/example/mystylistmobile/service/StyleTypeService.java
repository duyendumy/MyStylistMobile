package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.StyleTypeResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StyleTypeService {

    @GET("/my-stylist/style-types/{styleTypeId}")
    Call<ResponseModel<StyleTypeResponseDTO, ErrorDTO>> getStyleTypeById(@Path("styleTypeId") Long styleTypeId);


}
