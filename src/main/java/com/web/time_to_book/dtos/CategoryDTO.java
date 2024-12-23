package com.web.time_to_book.dtos;

import java.util.UUID;

public class CategoryDTO {
    UUID id;
    private String name;

    public CategoryDTO(String name) {
        this.name = name;
    }

    public CategoryDTO() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
