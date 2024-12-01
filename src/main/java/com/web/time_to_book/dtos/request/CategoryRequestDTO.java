package com.web.time_to_book.dtos.request;

public class CategoryRequestDTO {
    private String name;
    private String status;

    public CategoryRequestDTO(String name, String status) {
        this.name = name;
        this.status = status;
    }

    protected CategoryRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
