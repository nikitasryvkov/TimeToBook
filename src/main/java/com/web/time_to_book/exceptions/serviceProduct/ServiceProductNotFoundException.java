package com.web.time_to_book.exceptions.serviceProduct;

import java.util.UUID;

public class ServiceProductNotFoundException extends RuntimeException {
    public ServiceProductNotFoundException(UUID id) {
        super("Услуга '" + id + "' не существует!");
    }
}
