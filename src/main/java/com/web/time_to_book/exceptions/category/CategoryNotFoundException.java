package com.web.time_to_book.exceptions.category;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(UUID id) {
        super("Категория '" + id + "' не существует!");
    }
}
