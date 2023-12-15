package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.AddUserItemDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.model.UserItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface UserItemService {
    @GET("/my-stylist/user-items")
    Call<ResponseModel<List<UserItem>, ErrorDTO>> getAllUserItem();

    @POST("/my-stylist/user-items")
    Call<ResponseModel<UserItem, ErrorDTO>> addUserItem(@Body AddUserItemDTO item);
}
