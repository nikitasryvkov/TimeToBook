package com.web.time_to_book.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity {
    private LocalDateTime createdAt;
    private String text;
    private ServiceProduct service;
    private User createdBy;
    private Integer estimation;

    public Feedback(String text, ServiceProduct service, User createdBy, Integer estimation) {
        this.createdAt = LocalDateTime.now();
        this.text = text;
        this.service = service;
        this.createdBy = createdBy;
        this.estimation = estimation;
    }

    protected Feedback() {
    }

    @Column(name = "creation_date")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Column(name = "text")
    public String getText() {
        return text;
    }

    @ManyToOne
    @JoinColumn(name = "service_id")
    public ServiceProduct getService() {
        return service;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getCreatedBy() {
        return createdBy;
    }

    @Column(name = "estimation")
    public Integer getEstimation() {
        return estimation;
    }

    public void setCreatedAt(LocalDateTime creationDate) {
        this.createdAt = creationDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setService(ServiceProduct service) {
        this.service = service;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setEstimation(Integer estimation) {
        this.estimation = estimation;
    }
}
