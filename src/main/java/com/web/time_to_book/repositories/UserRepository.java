package com.web.time_to_book.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.web.time_to_book.models.User;

public interface UserRepository {
    Optional<User> findById(UUID id);

    User save(User user);

    User update(User user);

    List<User> findAll();

    Optional<UUID> findByName(String name);
}
