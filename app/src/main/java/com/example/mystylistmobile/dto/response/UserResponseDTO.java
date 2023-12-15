package com.example.mystylistmobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String email;
    private String name;

    private String roles;
    private String seasonalColorName;

    private String seasonalColorDescription;
    private Long seasonalColorId;
    private String bodyShapeName;
    private Long bodyShapeId;

    private String bodyShapeDescription;
    private String styleTypeName;
    private Long styleTypeId;

    private String styleTypeDescription;

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getSeasonalColorDescription() {
        return seasonalColorDescription;
    }

    public void setSeasonalColorDescription(String seasonalColorDescription) {
        this.seasonalColorDescription = seasonalColorDescription;
    }

    public String getBodyShapeDescription() {
        return bodyShapeDescription;
    }

    public void setBodyShapeDescription(String bodyShapeDescription) {
        this.bodyShapeDescription = bodyShapeDescription;
    }

    public String getStyleTypeDescription() {
        return styleTypeDescription;
    }

    public void setStyleTypeDescription(String styleTypeDescription) {
        this.styleTypeDescription = styleTypeDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeasonalColorName() {
        return seasonalColorName;
    }

    public void setSeasonalColorName(String seasonalColorName) {
        this.seasonalColorName = seasonalColorName;
    }

    public Long getSeasonalColorId() {
        return seasonalColorId;
    }

    public void setSeasonalColorId(Long seasonalColorId) {
        this.seasonalColorId = seasonalColorId;
    }

    public String getBodyShapeName() {
        return bodyShapeName;
    }

    public void setBodyShapeName(String bodyShapeName) {
        this.bodyShapeName = bodyShapeName;
    }

    public Long getBodyShapeId() {
        return bodyShapeId;
    }

    public void setBodyShapeId(Long bodyShapeId) {
        this.bodyShapeId = bodyShapeId;
    }

    public String getStyleTypeName() {
        return styleTypeName;
    }

    public void setStyleTypeName(String styleTypeName) {
        this.styleTypeName = styleTypeName;
    }

    public Long getStyleTypeId() {
        return styleTypeId;
    }

    public void setStyleTypeId(Long styleTypeId) {
        this.styleTypeId = styleTypeId;
    }
}
