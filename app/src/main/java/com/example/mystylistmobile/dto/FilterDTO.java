package com.example.mystylistmobile.dto;

public class FilterDTO {
    Integer filterBySeasonalColor;
    Integer filterByBodyShape;
    Integer filterByStyleType;

    public FilterDTO() {
    }

    public FilterDTO(Integer filterBySeasonalColor, Integer filterByBodyShape, Integer filterByStyleType) {
        this.filterBySeasonalColor = filterBySeasonalColor;
        this.filterByBodyShape = filterByBodyShape;
        this.filterByStyleType = filterByStyleType;
    }

    public Integer getFilterBySeasonalColor() {
        return filterBySeasonalColor;
    }

    public void setFilterBySeasonalColor(Integer filterBySeasonalColor) {
        this.filterBySeasonalColor = filterBySeasonalColor;
    }

    public Integer getFilterByBodyShape() {
        return filterByBodyShape;
    }

    public void setFilterByBodyShape(Integer filterByBodyShape) {
        this.filterByBodyShape = filterByBodyShape;
    }

    public Integer getFilterByStyleType() {
        return filterByStyleType;
    }

    public void setFilterByStyleType(Integer filterByStyleType) {
        this.filterByStyleType = filterByStyleType;
    }
}
