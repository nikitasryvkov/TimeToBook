package com.web.time_to_book.services;

import java.util.List;

import com.web.time_to_book.dtos.RoleDTO;
import com.web.time_to_book.models.enums.UserRoles;

public interface RoleService {
    List<RoleDTO> findAllRoles();

    RoleDTO findByName(UserRoles role);

    void save(RoleDTO role);

    long count();
}
