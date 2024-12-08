package com.web.time_to_book.models;

import java.time.LocalDateTime;

import com.web.time_to_book.models.enums.AppointmentStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appointment extends BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime recordTime;
    private User client;
    private User master;
    private ServiceProduct service;
    private AppointmentStatusEnum status;

    public Appointment(LocalDateTime recordTime, User client, User master, ServiceProduct service) {
        this.createdAt = LocalDateTime.now();
        this.recordTime = recordTime;
        this.client = client;
        this.master = master;
        this.service = service;
        this.status = AppointmentStatusEnum.WAITING_PAYMENT_CLIENT;
    }

    protected Appointment() {
    }

    @Column(name = "creation_date")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Column(name = "recording_date")
    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    @ManyToOne
    @JoinColumn(name = "client_id")
    public User getClient() {
        return client;
    }

    @ManyToOne
    @JoinColumn(name = "master_id")
    public User getMaster() {
        return master;
    }

    @ManyToOne
    @JoinColumn(name = "service_id")
    public ServiceProduct getService() {
        return service;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public AppointmentStatusEnum getStatus() {
        return status;
    }

    public void setCreatedAt(LocalDateTime creationDate) {
        this.createdAt = creationDate;
    }

    public void setRecordTime(LocalDateTime recordingDate) {
        this.recordTime = recordingDate;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public void setService(ServiceProduct service) {
        this.service = service;
    }

    public void setStatus(AppointmentStatusEnum status) {
        this.status = status;
    }
}
