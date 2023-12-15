package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.AddNewOutfitDTO;
import com.example.mystylistmobile.dto.AddUserItemDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.model.UserItem;
import com.example.mystylistmobile.model.UserOutfit;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserOutfitService {

    @GET("/my-stylist/user-outfits")
    Call<ResponseModel<List<UserOutfit>, ErrorDTO>> getAllUserOutfits();

    @POST("/my-stylist/user-outfits")
    Call<ResponseModel<UserOutfit, ErrorDTO>> addUserOutfit(@Body AddNewOutfitDTO outfit);

    @POST("/my-stylist/user-outfits/suggest")
    Call<ResponseModel<UserOutfit, ErrorDTO>> suggestUserOutfit(@Body AddUserItemDTO userItemDTO);
}
