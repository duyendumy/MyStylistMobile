package com.example.mystylistmobile.dto.response;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public class JwtResponse {

    @SerializedName("access")
    private TokenDTO access;

    @SerializedName("refresh")
    private TokenDTO refresh;

    public JwtResponse(TokenDTO access, TokenDTO refresh) {
        this.access = access;
        this.refresh = refresh;
    }

    public JwtResponse() {
    }

    public TokenDTO getAccess() {
        return access;
    }

    public TokenDTO getRefresh() {
        return refresh;
    }

    public void setAccess(TokenDTO access) {
        this.access = access;
    }

    public void setRefresh(TokenDTO refresh) {
        this.refresh = refresh;
    }
}
