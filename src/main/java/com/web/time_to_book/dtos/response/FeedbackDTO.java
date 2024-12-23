package com.web.time_to_book.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class FeedbackDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private String text;
    private UUID appointmentId;
    private String title;
    private UUID createdById;
    private String createdByFirstName;
    private UUID masterId;
    private String masterFirstName;
    private Integer estimation;

    public FeedbackDTO(LocalDateTime createdAt, String text, UUID appointmentId, String serviceTitle,
            UUID createdById, String createdByFirstName, UUID masterId, String masterFirstName, Integer estimation) {
        this.createdAt = createdAt;
        this.text = text;
        this.appointmentId = appointmentId;
        this.title = serviceTitle;
        this.createdById = createdById;
        this.createdByFirstName = createdByFirstName;
        this.masterId = masterId;
        this.masterFirstName = masterFirstName;
        this.estimation = estimation;
    }

    protected FeedbackDTO() {
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

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public String getCreatedByFirstName() {
        return createdByFirstName;
    }

    public UUID getMasterId() {
        return masterId;
    }

    public String getMasterFirstName() {
        return masterFirstName;
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

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setTitle(String serviceTitle) {
        this.title = serviceTitle;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public void setCreatedByFirstName(String createdByName) {
        this.createdByFirstName = createdByName;
    }

    public void setMasterId(UUID masterId) {
        this.masterId = masterId;
    }

    public void setMasterFirstName(String masterFirstName) {
        this.masterFirstName = masterFirstName;
    }

    public void setEstimation(Integer estimation) {
        this.estimation = estimation;
    }
}
