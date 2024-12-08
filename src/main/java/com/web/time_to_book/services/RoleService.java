package com.web.time_to_book.services;

import java.util.List;

import com.web.time_to_book.dtos.RoleDTO;

public interface RoleService {
    void addRole(RoleDTO role);

    void updateRole(RoleDTO role);

    List<RoleDTO> findAllRoles();
}
