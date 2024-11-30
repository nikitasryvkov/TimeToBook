package com.web.time_to_book.repositories;

import java.util.Optional;
import java.util.UUID;

import com.web.time_to_book.models.ServiceProduct;

public interface ServiceProductRepository {
    Optional<ServiceProduct> findById(UUID id);

    ServiceProduct save(ServiceProduct service);

    ServiceProduct update(ServiceProduct service);
}
