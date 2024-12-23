package com.web.time_to_book.exceptions.role;

import com.web.time_to_book.models.enums.UserRoles;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String name) {
        super("Роли '" + name + "' не существует!");
    }

    public RoleNotFoundException(UserRoles role) {
        super("Роли '" + role + "' не существует!");
    }
}
