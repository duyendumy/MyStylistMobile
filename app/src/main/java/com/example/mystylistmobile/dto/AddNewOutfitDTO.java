package com.example.mystylistmobile.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class AddNewOutfitDTO {

    @SerializedName("userItemIdList")
    private Set<Long> userItemIdList;

    public AddNewOutfitDTO() {
    }

    public AddNewOutfitDTO(Set<Long> userItemIdList) {
        this.userItemIdList = userItemIdList;
    }

    public Set<Long> getUserItemIdList() {
        return userItemIdList;
    }

    public void setUserItemIdList(Set<Long> userItemIdList) {
        this.userItemIdList = userItemIdList;
    }


}
