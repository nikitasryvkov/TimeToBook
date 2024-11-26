package com.web.time_to_book.models;

import com.web.time_to_book.models.enums.ServiceProductStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_service")
public class ServiceProduct extends BaseEntity {
    private String title;
    private String description;
    private Category category;
    private Long price;
    private User createdBy;
    private ServiceProductStatusEnum status;

    public ServiceProduct(String title, String description, Category category, Long price, User createdBy,
            ServiceProductStatusEnum status) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.createdBy = createdBy;
        this.status = status;
    }

    protected ServiceProduct() {
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @ManyToOne
    @JoinColumn(name = "service_category_id")
    public Category getCategory() {
        return category;
    }

    @Column(name = "price")
    public Long getPrice() {
        return price;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getCreatedBy() {
        return createdBy;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public ServiceProductStatusEnum getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setStatus(ServiceProductStatusEnum status) {
        this.status = status;
    }
}
