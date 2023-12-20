package com.example.mystylistmobile.dto;

public class UpdateUserDTO {
    private Float bust;

    private Float waist;

    private Float highHip;

    private Float hip;

    private String bodyShape;

    public UpdateUserDTO() {
    }

    public UpdateUserDTO(Float bust, Float waist, Float highHip, Float hip, String bodyShape) {
        this.bust = bust;
        this.waist = waist;
        this.highHip = highHip;
        this.hip = hip;
        this.bodyShape = bodyShape;
    }

    public Float getBust() {
        return bust;
    }

    public void setBust(Float bust) {
        this.bust = bust;
    }

    public Float getWaist() {
        return waist;
    }

    public void setWaist(Float waist) {
        this.waist = waist;
    }

    public Float getHighHip() {
        return highHip;
    }

    public void setHighHip(Float highHip) {
        this.highHip = highHip;
    }

    public Float getHip() {
        return hip;
    }

    public void setHip(Float hip) {
        this.hip = hip;
    }

    public String getBodyShape() {
        return bodyShape;
    }

    public void setBodyShape(String bodyShape) {
        this.bodyShape = bodyShape;
    }
}
