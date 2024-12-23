package com.web.time_to_book.models;

import com.web.time_to_book.models.enums.UserRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    private UserRoles name;

    public Role(UserRoles name) {
        this.name = name;
    }

    protected Role() {
    }

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    public UserRoles getName() {
        return name;
    }

    public void setName(UserRoles name) {
        this.name = name;
    }
}
