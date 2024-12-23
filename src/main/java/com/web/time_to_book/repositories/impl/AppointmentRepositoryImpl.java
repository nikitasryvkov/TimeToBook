package com.web.time_to_book.repositories.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.web.time_to_book.models.Appointment;
import com.web.time_to_book.models.enums.AppointmentStatusEnum;
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
    public List<Appointment> findAllByUserId(UUID id) {
        return entityManager
                .createQuery("SELECT a FROM Appointment a WHERE a.client.id = :id AND a.status = :status",
                        Appointment.class)
                .setParameter("id", id).setParameter("status", AppointmentStatusEnum.DONE).getResultList();
    }

    @Override
    public List<Appointment> findAllByMasterId(UUID id) {
        return entityManager
                .createQuery("SELECT a FROM Appointment a WHERE a.master.id = :id",
                        Appointment.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<Appointment> findAllByUserAndStatus(UUID id, AppointmentStatusEnum status) {
        return entityManager
                .createQuery("SELECT DISTINCT a FROM Appointment a WHERE a.client.id = :id AND a.status = :status",
                        Appointment.class)
                .setParameter("id", id).setParameter("status", AppointmentStatusEnum.DONE).getResultList();
    }
}
