package com.web.time_to_book.repositories.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.web.time_to_book.models.Role;
import com.web.time_to_book.models.enums.UserRoles;
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
    public Optional<Role> findByName(UserRoles role) {
        return Optional.ofNullable(entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", role)
                .getSingleResult());
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(r) FROM Role r", Long.class).getSingleResult();
    }
}
