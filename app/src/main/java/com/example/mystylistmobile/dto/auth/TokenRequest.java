package com.example.mystylistmobile.dto.auth;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

public class TokenRequest {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenRequest(String token){
        this.token = token;
    }

    public TokenRequest() {
    }
}
