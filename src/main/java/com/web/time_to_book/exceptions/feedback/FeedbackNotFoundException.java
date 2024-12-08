package com.web.time_to_book.exceptions.feedback;

import java.util.UUID;

public class FeedbackNotFoundException extends RuntimeException {
    public FeedbackNotFoundException(UUID id) {
        super("Отзыв '" + id + "' не существует!");
    }
}
