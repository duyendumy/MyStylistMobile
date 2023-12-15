package com.example.mystylistmobile.dto;

public class AddNewItemCategoryDTO {
    private String name;

    private Long parent;

    public AddNewItemCategoryDTO() {
    }

    public AddNewItemCategoryDTO(String name, Long parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
