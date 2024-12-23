package com.web.time_to_book.services;

import java.util.List;
import java.util.UUID;

import com.web.time_to_book.dtos.request.ServiceProductRequestDTO;
import com.web.time_to_book.dtos.response.ServiceProductDTO;
import com.web.time_to_book.dtos.response.ServiceProductResponseDTO;
import com.web.time_to_book.models.enums.AppointmentStatusEnum;

public interface ServiceProductService {
    void addServiceProduct(ServiceProductRequestDTO serviceProductDTO);

    void updateServiceProduct(UUID id, ServiceProductRequestDTO serviceProductDTO);

    List<ServiceProductResponseDTO> findAllServices();

    List<ServiceProductResponseDTO> findAllServicesMasters(UUID id);

    ServiceProductDTO findById(UUID id);

    List<ServiceProductResponseDTO> getServicesByCategory(UUID categoryId);

    List<ServiceProductResponseDTO> getServicesByStatusAndUser(UUID userId, AppointmentStatusEnum status);
}
