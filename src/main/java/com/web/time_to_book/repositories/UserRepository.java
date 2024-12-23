package com.web.time_to_book.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.web.time_to_book.models.User;

public interface UserRepository {
    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String name);

    Optional<User> findByEmail(String email);

    User save(User user);

    User update(User user);

    List<User> findAll();

    Optional<UUID> findIdByUsername(String name);

    List<User> findAllMasters();

    Integer countAppointment(UUID id);
}
