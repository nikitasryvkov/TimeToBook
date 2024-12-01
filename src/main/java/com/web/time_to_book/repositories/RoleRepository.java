package com.web.time_to_book.repositories;

import java.util.List;
import java.util.Optional;

import com.web.time_to_book.models.Role;

public interface RoleRepository {
    Optional<Role> findByName(String name);

    Role save(Role role);

    List<Role> findAll();
}
