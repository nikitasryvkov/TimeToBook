package com.web.time_to_book.repositories.impl;

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
}
