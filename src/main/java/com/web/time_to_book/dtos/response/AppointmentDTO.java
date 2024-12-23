package com.web.time_to_book.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class AppointmentDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime recordTime;
    private UUID clientId;
    private String clientFirstName;
    private UUID masterId;
    private String masterFirstName;
    private UUID serviceId;
    private String serviceTitle;
    private String status;

    public AppointmentDTO(UUID id, LocalDateTime createdAt, LocalDateTime recordTime, UUID clientId,
            String clientName, UUID masterId, String masterName, UUID serviceId, String serviceTitle, String status) {
        this.id = id;
        this.createdAt = createdAt;
        this.recordTime = recordTime;
        this.clientId = clientId;
        this.clientFirstName = clientName;
        this.masterId = masterId;
        this.masterFirstName = masterName;
        this.serviceId = serviceId;
        this.serviceTitle = serviceTitle;
        this.status = status;
    }

    protected AppointmentDTO() {
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @NotNull(message = "Дата обязательна")
    @PastOrPresent(message = "Некорректная дата")
    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public UUID getClientId() {
        return clientId;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public UUID getMasterId() {
        return masterId;
    }

    public String getMasterFirstName() {
        return masterFirstName;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setClientFirstName(String clientName) {
        this.clientFirstName = clientName;
    }

    public void setMasterId(UUID masterId) {
        this.masterId = masterId;
    }

    public void setMasterFirstName(String masterName) {
        this.masterFirstName = masterName;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
