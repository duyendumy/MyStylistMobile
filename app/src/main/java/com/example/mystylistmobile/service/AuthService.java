package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.ForgotPasswordRequest;
import com.example.mystylistmobile.dto.ResetPasswordRequest;
import com.example.mystylistmobile.dto.auth.LoginRequest;
import com.example.mystylistmobile.dto.auth.TokenRequest;
import com.example.mystylistmobile.dto.auth.UserCreateDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.JwtResponse;
import com.example.mystylistmobile.dto.response.RegisterV2ResponseDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/my-stylist/auth/login")
    Call<RegisterV2ResponseDTO> login(@Body LoginRequest loginRequest);

    @POST("/my-stylist/auth/register")
    Call<Void> register(@Body UserCreateDTO userCreateDTO);

    @POST("/my-stylist/auth/logout")
    Call<Void> logout(@Body TokenRequest tokenRequest);

    @POST("/my-stylist/auth/refresh-tokens")
    Call<ResponseModel<JwtResponse,ErrorDTO>> refreshToken(@Body TokenRequest tokenRequest);

    @POST("/my-stylist/auth/forgot-password")
    Call<Void> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("/my-stylist/auth/reset-password")
    Call<Void> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);
}
