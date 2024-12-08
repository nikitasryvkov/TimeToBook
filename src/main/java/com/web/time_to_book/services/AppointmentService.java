package com.web.time_to_book.services;

import java.util.List;
import java.util.UUID;

import com.web.time_to_book.dtos.request.AppointmentRequestDTO;
import com.web.time_to_book.dtos.response.AppointmentDTO;
import com.web.time_to_book.dtos.response.AppointmentResponseDTO;

public interface AppointmentService {
    void addAppointment(AppointmentRequestDTO appointmentDTO);

    void updateAppointment(UUID id, AppointmentRequestDTO appointmentDTO);

    List<AppointmentResponseDTO> findAllAppointments();

    List<AppointmentResponseDTO> findAllAppointmentsById(UUID id);

    AppointmentDTO findById(UUID id);
}
