package com.example.mystylistmobile.model;

import com.google.gson.annotations.SerializedName;

public class UserItem {

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("category")
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserItem() {
    }

    public UserItem(Long id, String name, String image, String category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
    }
}
