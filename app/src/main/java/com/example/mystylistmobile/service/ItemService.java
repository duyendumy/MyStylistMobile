package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.AddNewItemDTO;
import com.example.mystylistmobile.dto.FilterDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ItemResponseDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;


import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ItemService {

    @GET("/my-stylist/items")
    Call<ResponseModel<List<ItemResponseDTO>, ErrorDTO>> getAllItems();

    @POST("/my-stylist/items")
    Call<ResponseModel<ItemResponseDTO, ErrorDTO>> addItem(@Body AddNewItemDTO item);

    @POST("/my-stylist/items/suggest/{parent}")
    Call<ResponseModel<List<ItemResponseDTO>, ErrorDTO>> suggestItemsOfParentCategory(@Path("parent") Long parent,
                                                                                      @Body FilterDTO filterDTO);
}
