package com.web.time_to_book.models;

import com.web.time_to_book.models.enums.CategoryStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_category")
public class Category extends BaseEntity {
    private String name;
    private CategoryStatusEnum status;

    public Category(String name, CategoryStatusEnum status) {
        this.name = name;
        this.status = status;
    }

    protected Category() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public CategoryStatusEnum getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(CategoryStatusEnum status) {
        this.status = status;
    }
}
