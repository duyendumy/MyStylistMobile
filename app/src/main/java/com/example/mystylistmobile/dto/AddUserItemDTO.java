package com.example.mystylistmobile.dto;

import com.google.gson.annotations.SerializedName;

public class AddUserItemDTO {

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("itemCategory")
    private Long itemCategory;

    public AddUserItemDTO() {
    }

    public AddUserItemDTO(String name, String image, Long itemCategory) {
        this.name = name;
        this.image = image;
        this.itemCategory = itemCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(Long itemCategory) {
        this.itemCategory = itemCategory;
    }
}
