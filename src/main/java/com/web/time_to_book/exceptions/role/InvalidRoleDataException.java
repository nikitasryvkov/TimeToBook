package com.web.time_to_book.exceptions.role;

public class InvalidRoleDataException extends RuntimeException {
    public InvalidRoleDataException() {
        super("Некорректные данные для роли!");
    }
}
