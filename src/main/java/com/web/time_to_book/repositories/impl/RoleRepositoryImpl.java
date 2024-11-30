package com.web.time_to_book.repositories.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.web.time_to_book.models.Role;
import com.web.time_to_book.repositories.RoleRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class RoleRepositoryImpl extends CRUDRepository<Role> implements RoleRepository {

    @PersistenceContext
    public EntityManager entityManager;

    protected RoleRepositoryImpl() {
        super(Role.class);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return Optional.ofNullable(entityManager.createQuery("SELECT r FROM Role WHERE r.name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult());
    }
}
