package com.web.time_to_book.exceptions.role;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String name) {
        super("Роли '" + name + "' не существует!");
    }
}
