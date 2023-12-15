package com.example.mystylistmobile.model;

public class Color {
    private String code;
    private int imgid;

    public Color(String code, int imgid) {
        this.code = code;
        this.imgid = imgid;
    }

    public String getCode() {
        return code;
    }

    public void getCode(String code) {
        this.code = code;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
