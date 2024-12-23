package com.web.time_to_book.repositories;

import java.util.List;
import java.util.Optional;

import com.web.time_to_book.models.Role;
import com.web.time_to_book.models.enums.UserRoles;

public interface RoleRepository {
    Optional<Role> findByName(UserRoles role);

    Role save(Role role);

    Role update(Role role);

    List<Role> findAll();

    long count();
}
