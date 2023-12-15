package com.example.mystylistmobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserOutfit {

    @SerializedName("id")
    private Long id;

    @SerializedName("userItems")
    private ArrayList<UserItem> userItems;

    public UserOutfit() {
    }

    public UserOutfit(Long id, ArrayList<UserItem> userItems) {
        this.id = id;
        this.userItems = userItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<UserItem> getUserItems() {
        return userItems;
    }

    public void setUserItems(ArrayList<UserItem> userItems) {
        this.userItems = userItems;
    }
}
