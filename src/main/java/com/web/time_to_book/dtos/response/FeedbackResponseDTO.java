package com.web.time_to_book.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class FeedbackResponseDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private String text;
    private UUID serviceId;
    private String serviceTitle;
    private UUID createdById;
    private String createdByFirstName;
    private Integer estimation;

    public FeedbackResponseDTO(LocalDateTime createdAt, String text, UUID serviceId,
            String serviceTitle,
            UUID createdById, String createdByName, Integer estimation) {
        this.createdAt = createdAt;
        this.text = text;
        this.serviceId = serviceId;
        this.serviceTitle = serviceTitle;
        this.createdById = createdById;
        this.createdByFirstName = createdByName;
        this.estimation = estimation;
    }

    protected FeedbackResponseDTO() {
    }

    public UUID getId() {
        return id;
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

    public String getCreatedByFirstName() {
        return createdByFirstName;
    }

    public Integer getEstimation() {
        return estimation;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setCreatedByFirstName(String createdByName) {
        this.createdByFirstName = createdByName;
    }

    public void setEstimation(Integer estimation) {
        this.estimation = estimation;
    }
}
