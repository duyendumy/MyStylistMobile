package com.example.mystylistmobile.dto.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryPaging {

    @SerializedName("categories")
    ArrayList<CategoryResponseDTO> categories;

    @SerializedName("pageNumber")
    int pageNumber;

    @SerializedName("pageSize")
    int pageSize;

    @SerializedName("totalElements")
    int totalElements;

    @SerializedName("totalPages")
    int totalPages;

    public CategoryPaging() {
    }

    public CategoryPaging(ArrayList<CategoryResponseDTO> categories, int pageNumber, int pageSize, int totalElements, int totalPages) {
        this.categories = categories;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public ArrayList<CategoryResponseDTO> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryResponseDTO> categories) {
        this.categories = categories;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
