package com.web.time_to_book.dtos.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentRequestDTO {
    private LocalDateTime createdAt;
    private LocalDateTime recordTime;
    private UUID clientId;
    private UUID masterId;
    private UUID serviceId;
    private String status;

    public AppointmentRequestDTO(LocalDateTime createdAt, LocalDateTime recordTime, UUID clientId, UUID masterId,
            UUID serviceId, String status) {
        this.createdAt = createdAt;
        this.recordTime = recordTime;
        this.clientId = clientId;
        this.masterId = masterId;
        this.serviceId = serviceId;
        this.status = status;
    }

    public AppointmentRequestDTO(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }

    protected AppointmentRequestDTO() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getMasterId() {
        return masterId;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public void setMasterId(UUID masterId) {
        this.masterId = masterId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
