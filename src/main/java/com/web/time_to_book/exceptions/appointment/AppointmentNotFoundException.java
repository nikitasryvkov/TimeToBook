package com.web.time_to_book.exceptions.appointment;

import java.util.UUID;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(UUID id) {
        super("Запись '" + id + "' не существует!");
    }
}
