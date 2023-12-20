package com.example.mystylistmobile.dto;

public class AddNewItemDTO {
    private String name;

    private String image;

    private Long categoryId;

    private Long bodyShapeId;

    private Long seasonalColorId;

    private Long styleTypeId;

    public AddNewItemDTO() {
    }

    public AddNewItemDTO(String name, String image, Long categoryId, Long bodyShapeId, Long seasonalColorId, Long styleTypeId) {
        this.name = name;
        this.image = image;
        this.categoryId = categoryId;
        this.bodyShapeId = bodyShapeId;
        this.seasonalColorId = seasonalColorId;
        this.styleTypeId = styleTypeId;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBodyShapeId() {
        return bodyShapeId;
    }

    public void setBodyShapeId(Long bodyShapeId) {
        this.bodyShapeId = bodyShapeId;
    }

    public Long getSeasonalColorId() {
        return seasonalColorId;
    }

    public void setSeasonalColorId(Long seasonalColorId) {
        this.seasonalColorId = seasonalColorId;
    }

    public Long getStyleTypeId() {
        return styleTypeId;
    }

    public void setStyleTypeId(Long styleTypeId) {
        this.styleTypeId = styleTypeId;
    }
}
