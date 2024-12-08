package com.web.time_to_book.repositories.impl;

import java.util.List;
import java.util.UUID;

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

    @Override
    public List<Appointment> findAll() {
        return entityManager.createQuery("SELECT a FROM Appointment a", Appointment.class).getResultList();
    }

    @Override
    public List<Appointment> findAllBy(UUID id) {
        return entityManager.createQuery("SELECT a FROM Appointment a WHERE a.clientId = :id", Appointment.class)
                .setParameter("id", id).getResultList();
    }
}
