package com.web.time_to_book.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.web.time_to_book.models.Appointment;
import com.web.time_to_book.models.enums.AppointmentStatusEnum;

public interface AppointmentRepository {
    Optional<Appointment> findById(UUID id);

    Appointment save(Appointment service);

    Appointment update(Appointment service);

    List<Appointment> findAll();

    List<Appointment> findAllByUserId(UUID id);

    List<Appointment> findAllByMasterId(UUID id);

    List<Appointment> findAllByUserAndStatus(UUID id, AppointmentStatusEnum status);
}
