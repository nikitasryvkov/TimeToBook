package com.web.time_to_book.repositories.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.web.time_to_book.models.User;
import com.web.time_to_book.models.enums.AppointmentStatusEnum;
import com.web.time_to_book.models.enums.UserRoles;
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

    @Override
    public Optional<User> findByUsername(String name) {
        return Optional
                .ofNullable(entityManager.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class)
                        .setParameter("name", name).getSingleResult());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email).getSingleResult());
    }

    @Override
    public Optional<UUID> findIdByUsername(String name) {
        return Optional
                .ofNullable(entityManager.createQuery("SELECT u.id FROM User u WHERE u.username = : name", UUID.class)
                        .setParameter("name", name).getSingleResult());
    }

    @Override
    public List<User> findAllMasters() {
        return entityManager
                .createQuery("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.name = :roleName",
                        User.class)
                .setParameter("roleName", UserRoles.MODERATOR)
                .getResultList();
    }

    @Override
    public Integer countAppointment(UUID id) {
        return entityManager
                .createQuery("SELECT COUNT(a) FROM Appointment a WHERE a.client.id = :id AND a.status = :status",
                        Long.class)
                .setParameter("id", id).setParameter("status", AppointmentStatusEnum.DONE).getSingleResult().intValue();
    }
}
