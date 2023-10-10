package com.example.mystylistmobile.dto.response;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import retrofit2.http.GET;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseModel<T,E> {
    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("response")
    private T response;

    @SerializedName("errors")
    private E errors;

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public T getResponse() {
        return response;
    }

    public E getErrors() {
        return errors;
    }
}
