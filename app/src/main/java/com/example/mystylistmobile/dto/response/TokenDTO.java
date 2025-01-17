package com.example.mystylistmobile.dto.response;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
public class TokenDTO {
    @SerializedName("token")
    private String token;
    @SerializedName("expires")
    private String expires;

    public TokenDTO() {
    }

    public TokenDTO(String token, String expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
