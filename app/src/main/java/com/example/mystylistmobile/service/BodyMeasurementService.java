package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.UpdateUserDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.UserResponseDTO;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BodyMeasurementService {
    @POST("/my-stylist/body-measurement")
    Call<ResponseModel<UserResponseDTO, ErrorDTO>> submitBodyMeasurement(@Body UpdateUserDTO updateUserDTO);
}
