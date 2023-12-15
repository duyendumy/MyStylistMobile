package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ItemResponseDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemService {

    @GET("/my-stylist/items")
    Call<ResponseModel<List<ItemResponseDTO>, ErrorDTO>> getAllItems();
}
