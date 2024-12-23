package com.web.time_to_book.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.web.time_to_book.models.ServiceProduct;
import com.web.time_to_book.models.enums.AppointmentStatusEnum;

public interface ServiceProductRepository {
    Optional<ServiceProduct> findById(UUID id);

    ServiceProduct save(ServiceProduct service);

    ServiceProduct update(ServiceProduct service);

    List<ServiceProduct> findAll();

    List<ServiceProduct> findAllByMaster(UUID id);

    List<ServiceProduct> findByCategoryId(UUID id);

    List<ServiceProduct> findByStatusAndUserId(UUID id, AppointmentStatusEnum status);
}
