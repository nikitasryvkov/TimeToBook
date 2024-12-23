package com.web.time_to_book.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity {
    private LocalDateTime createdAt;
    private String text;
    private Appointment appointment;
    private Integer estimation;

    public Feedback(String text, Appointment appointment, Integer estimation) {
        this.createdAt = LocalDateTime.now();
        this.text = text;
        this.appointment = appointment;
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

    @OneToOne
    @JoinColumn(name = "appointment_id")
    public Appointment getAppointment() {
        return appointment;
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

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setEstimation(Integer estimation) {
        this.estimation = estimation;
    }
}
