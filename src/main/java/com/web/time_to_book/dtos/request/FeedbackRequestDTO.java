package com.web.time_to_book.dtos.request;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FeedbackRequestDTO {
    private LocalDateTime createdAt;
    private String text;
    private UUID appointmentId;
    private Integer estimation;

    public FeedbackRequestDTO(String text, UUID appointmentId, Integer estimation) {
        this.text = text;
        this.appointmentId = appointmentId;
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

    @NotBlank
    public String getText() {
        return text;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    @NotNull
    public Integer getEstimation() {
        return estimation;
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

    public void setEstimation(Integer estimation) {
        this.estimation = estimation;
    }
}
