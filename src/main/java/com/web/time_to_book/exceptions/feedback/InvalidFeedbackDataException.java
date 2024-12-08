package com.web.time_to_book.exceptions.feedback;

public class InvalidFeedbackDataException extends RuntimeException {
    public InvalidFeedbackDataException() {
        super("Некорректные данные для отзыва!");
    }
}
