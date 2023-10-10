package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.auth.LoginRequest;
import com.example.mystylistmobile.dto.auth.UserCreateDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.RegisterV2ResponseDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.UserPaging;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth/login")
    Call<RegisterV2ResponseDTO> login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<Void> register(@Body LoginRequest loginRequest);
}
