package com.web.time_to_book.dtos.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class FeedbackRequestDTO {
    private LocalDateTime createdAt;
    private String text;
    private UUID serviceId;
    private UUID createdById;
    private Integer estimation;

    public FeedbackRequestDTO(String text, UUID serviceId, UUID createdById, Integer estimation) {
        this.text = text;
        this.serviceId = serviceId;
        this.createdById = createdById;
        this.estimation = estimation;
    }

    public FeedbackRequestDTO(String text, Integer estimation) {
        this.text = text;
        this.estimation = estimation;
    }

    protected FeedbackRequestDTO() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public Integer getEstimation() {
        return estimation;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public void setEstimation(Integer estimation) {
        this.estimation = estimation;
    }
}
