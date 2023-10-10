package com.example.mystylistmobile.dto.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserPaging {
    @SerializedName("users")
    List<UserResponseDTO> users;

    @SerializedName("pageNumber")
    int pageNumber;

    @SerializedName("pageSize")
    int pageSize;

    @SerializedName("totalElements")
    int totalElements;

    @SerializedName("totalPages")
    int totalPages;

    public UserPaging() {
    }

    public UserPaging(List<UserResponseDTO> users, int pageNumber, int pageSize, int totalElements, int totalPages) {
        this.users = users;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<UserResponseDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponseDTO> users) {
        this.users = users;
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
