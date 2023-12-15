package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.AddNewItemCategoryDTO;
import com.example.mystylistmobile.dto.response.CategoryResponseDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface CategoryService {

/*    @GET("/my-stylist/categories")
    Call<ResponseModel<CategoryPaging, ErrorDTO>> getAllCategory(@Query("parent") Long parent, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize, @Query("sortField") String sortField);*/

    @GET("/my-stylist/categories")
    Call<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> getAllCategory();

    @POST("/my-stylist/categories")
    Call<ResponseModel<CategoryResponseDTO, ErrorDTO>> addCategory(@Body AddNewItemCategoryDTO categoryDTO);

    @GET("/my-stylist/categories/{parent}")
    Call<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> getSubCategoryOfCategory(@Path("parent") Long parent);
}
