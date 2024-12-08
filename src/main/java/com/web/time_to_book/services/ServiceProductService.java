package com.web.time_to_book.services;

import java.util.List;
import java.util.UUID;

import com.web.time_to_book.dtos.request.ServiceProductRequestDTO;
import com.web.time_to_book.dtos.response.ServiceProductDTO;
import com.web.time_to_book.dtos.response.ServiceProductResponseDTO;

public interface ServiceProductService {
    void addServiceProduct(ServiceProductRequestDTO serviceProductDTO);

    void updateServiceProduct(UUID id, ServiceProductRequestDTO serviceProductDTO);

    List<ServiceProductResponseDTO> findAllServices();

    ServiceProductDTO findById(UUID id);
}
