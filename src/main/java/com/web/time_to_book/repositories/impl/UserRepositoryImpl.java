package com.web.time_to_book.repositories.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.web.time_to_book.models.User;
import com.web.time_to_book.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserRepositoryImpl extends CRUDRepository<User> implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    protected UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
