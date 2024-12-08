package com.web.time_to_book.exceptions.serviceProduct;

public class InvalidServiceProductDataException extends RuntimeException {
    public InvalidServiceProductDataException() {
        super("Некорректные данные для услуги!");
    }
}
