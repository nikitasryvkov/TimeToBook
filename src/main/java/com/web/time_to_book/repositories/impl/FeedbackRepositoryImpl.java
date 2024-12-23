package com.web.time_to_book.repositories.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.web.time_to_book.models.Feedback;
import com.web.time_to_book.repositories.FeedbackRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class FeedbackRepositoryImpl extends CRUDRepository<Feedback> implements FeedbackRepository {

    @PersistenceContext
    private EntityManager entityManager;

    protected FeedbackRepositoryImpl() {
        super(Feedback.class);
    }

    @Override
    public List<Feedback> findAll() {
        return entityManager.createQuery("SELECT f FROM Feedback f", Feedback.class).getResultList();
    }

    @Override
    public List<Feedback> findAllByUserId(UUID id) {
        return entityManager
                .createQuery("SELECT f FROM Feedback f WHERE f.appointment.client.id = :id", Feedback.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<Feedback> findAllByMasterId(UUID id) {
        return entityManager.createQuery("SELECT f FROM Feedback f WHERE f.appointment.master.id = :id", Feedback.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<Feedback> findAllByServiceId(UUID id) {
        return entityManager
                .createQuery("SELECT f FROM Feedback f WHERE f.appointment.service.id = :id", Feedback.class)
                .setParameter("id", id).getResultList();
    }
}
