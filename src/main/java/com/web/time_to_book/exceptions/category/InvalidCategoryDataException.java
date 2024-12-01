package com.web.time_to_book.exceptions.category;

public class InvalidCategoryDataException extends RuntimeException {
    public InvalidCategoryDataException() {
        super("Некорректные данные для категории!");
    }
}
