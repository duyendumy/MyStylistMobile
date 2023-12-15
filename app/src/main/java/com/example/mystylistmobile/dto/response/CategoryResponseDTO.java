package com.example.mystylistmobile.dto.response;


import com.google.gson.annotations.SerializedName;

public class CategoryResponseDTO {

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("parent")
    private Long parent;

    public CategoryResponseDTO(Long id, String name, Long parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public CategoryResponseDTO() {
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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
