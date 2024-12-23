package com.web.time_to_book.dtos;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.web.time_to_book.models.enums.UserRoles;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RoleDTO {
    private UUID id;
    private UserRoles name;

    public RoleDTO(UserRoles name) {
        this.name = name;
    }

    public RoleDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @NotNull
    @NotEmpty
    @Length(min = 3, message = "Role name must be more than two characters")
    public UserRoles getName() {
        return name;
    }

    public void setName(UserRoles name) {
        this.name = name;
    }
}
