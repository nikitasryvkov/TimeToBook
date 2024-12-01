package com.web.time_to_book.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.time_to_book.dtos.RoleDTO;
import com.web.time_to_book.exceptions.role.InvalidRoleDataException;
import com.web.time_to_book.models.Role;
import com.web.time_to_book.repositories.RoleRepository;
import com.web.time_to_book.services.RoleService;
import com.web.time_to_book.utils.validation.ValidationUtil;

import jakarta.validation.ConstraintViolation;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public RoleServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setRoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addRole(RoleDTO roleDTO) {
        if (!validationUtil.isValid(roleDTO)) {
            this.validationUtil
                    .violations(roleDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            throw new InvalidRoleDataException();
        }

        Role role = modelMapper.map(roleDTO, Role.class);
        roleRepository.save(role);
    }

    @Override
    public List<RoleDTO> findAllRoles() {
        return roleRepository.findAll().stream().map(role -> modelMapper.map(role, RoleDTO.class)).toList();
    }

}
