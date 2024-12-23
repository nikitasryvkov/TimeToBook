package com.web.time_to_book.services.impl;

import java.util.List;

import com.web.time_to_book.exceptions.role.RoleNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.time_to_book.dtos.RoleDTO;
import com.web.time_to_book.models.Role;
import com.web.time_to_book.models.enums.UserRoles;
import com.web.time_to_book.repositories.RoleRepository;
import com.web.time_to_book.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setRoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoleDTO> findAllRoles() {
        return roleRepository.findAll().stream().map(role -> modelMapper.map(role, RoleDTO.class)).toList();
    }

    @Override
    public RoleDTO findByName(UserRoles roleDTO) {
        Role role = roleRepository.findByName(roleDTO)
                .orElseThrow(() -> new RoleNotFoundException(roleDTO));
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public void save(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        roleRepository.save(role);
    }

    @Override
    public long count() {
        return roleRepository.count();
    }
}
