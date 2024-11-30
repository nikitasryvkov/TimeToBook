package com.web.time_to_book.exceptions.user;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("Пользователь с id: " + " не существует");
    }
}
