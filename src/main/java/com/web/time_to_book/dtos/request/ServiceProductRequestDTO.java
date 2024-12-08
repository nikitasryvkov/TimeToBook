package com.web.time_to_book.dtos.request;

import java.util.UUID;

public class ServiceProductRequestDTO {
    private String title;
    private String description;
    private UUID categoryId;
    private Long price;
    private UUID createdById;

    public ServiceProductRequestDTO(String title, String description, UUID categoryId, Long price, UUID createdById) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.createdById = createdById;
    }

    public ServiceProductRequestDTO() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public Long getPrice() {
        return price;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }
}
