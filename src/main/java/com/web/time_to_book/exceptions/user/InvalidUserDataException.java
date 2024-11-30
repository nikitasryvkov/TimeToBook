package com.web.time_to_book.exceptions.user;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException() {
        super("Некорректные данные для пользователя");
    }
}
