package com.example.mystylistmobile.helper;

import com.example.mystylistmobile.model.UserOutfit;

public class DataManager {
    private static DataManager instance;
    private UserOutfit userOutfit;

    private DataManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public UserOutfit getUserOutfit() {
        return userOutfit;
    }

    public void setUserOutfit(UserOutfit userOutfit) {
        this.userOutfit = userOutfit;
    }
}
