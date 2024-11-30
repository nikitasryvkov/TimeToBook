package com.web.time_to_book.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public abstract class CRUDRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityClass;

    public CRUDRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Transactional
    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }
}
