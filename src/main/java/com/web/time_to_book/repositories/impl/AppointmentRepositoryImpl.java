package com.web.time_to_book.repositories.impl;

import org.springframework.stereotype.Repository;

import com.web.time_to_book.models.Appointment;
import com.web.time_to_book.repositories.AppointmentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AppointmentRepositoryImpl extends CRUDRepository<Appointment> implements AppointmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    protected AppointmentRepositoryImpl() {
        super(Appointment.class);
    }
}
