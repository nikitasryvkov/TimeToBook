package com.web.time_to_book.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class FeedbackDTO {
    private LocalDateTime createdAt;
    private String text;
    private UUID serviceId;
    private String serviceTitle;
    private UUID createdById;
    private String createdByName;
    private Integer estimation;

    public FeedbackDTO(LocalDateTime createdAt, String text, UUID serviceId, String serviceTitle,
            UUID createdById, String createdByName, Integer estimation) {
        this.createdAt = createdAt;
        this.text = text;
        this.serviceId = serviceId;
        this.serviceTitle = serviceTitle;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.estimation = estimation;
    }

    protected FeedbackDTO() {
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

    public String getServiceTitle() {
        return serviceTitle;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public String getCreatedByName() {
        return createdByName;
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

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public void setEstimation(Integer estimation) {
        this.estimation = estimation;
    }
}
