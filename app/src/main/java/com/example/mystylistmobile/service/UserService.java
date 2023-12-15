package com.example.mystylistmobile.service;

import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.UserPaging;
import com.example.mystylistmobile.dto.response.UserResponseDTO;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    /*@GET("/my-stylist/users")
    Call<ResponseModel<UserPaging, ErrorDTO> > getAllUsers(@Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize, @Query("sortField") String sortField);
*/

    @GET("/my-stylist/users")
    Call<ResponseModel<List<UserResponseDTO>, ErrorDTO>> getAllUsers();
    @GET("/my-stylist/users/information")
    Call<ResponseModel<UserResponseDTO, ErrorDTO> > getUserInformation();
}
