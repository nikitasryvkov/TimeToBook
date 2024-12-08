package com.web.time_to_book.exceptions.appointment;

public class InvalidAppointmentDataException extends RuntimeException {
    public InvalidAppointmentDataException() {
        super("Некорректные данные для записи!");
    }
}
