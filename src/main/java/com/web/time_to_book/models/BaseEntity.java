package com.web.time_to_book.models;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
    private UUID id;

    @Id
    @UuidGenerator
    @Column(name = "id")
    public UUID getId() {
        return id;
    }

    protected void setId(UUID id) {
        this.id = id;
    }
}
