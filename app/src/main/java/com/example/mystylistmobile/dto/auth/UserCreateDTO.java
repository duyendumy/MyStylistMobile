package com.example.mystylistmobile.dto.auth;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserCreateDTO {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    public UserCreateDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserCreateDTO(){}
}
