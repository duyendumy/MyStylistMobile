package com.example.mystylistmobile.dto.response;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SeasonalColorResponseDTO {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("colorPalette")
    private String colorPalette;

    public SeasonalColorResponseDTO() {
    }

    public SeasonalColorResponseDTO(Long id, String name, String description, String colorPalette) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.colorPalette = colorPalette;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColorPalette() {
        return colorPalette;
    }

    public void setColorPalette(String colorPalette) {
        this.colorPalette = colorPalette;
    }
}
