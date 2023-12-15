package com.example.mystylistmobile.model;

public class OutfitCloset {
    private int topImage;
    private int bottomImage;

    public OutfitCloset(int topImage, int bottomImage) {
        this.topImage = topImage;
        this.bottomImage = bottomImage;
    }

    public int getTopImage() {
        return topImage;
    }

    public void setTopImage(int topImage) {
        this.topImage = topImage;
    }

    public int getBottomImage() {
        return bottomImage;
    }

    public void setBottomImage(int bottomImage) {
        this.bottomImage = bottomImage;
    }
}
