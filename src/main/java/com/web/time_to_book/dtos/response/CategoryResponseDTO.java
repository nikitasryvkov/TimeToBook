package com.web.time_to_book.dtos.response;

public class CategoryResponseDTO {
    private String name;

    public CategoryResponseDTO(String name) {
        this.name = name;
    }

    protected CategoryResponseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
