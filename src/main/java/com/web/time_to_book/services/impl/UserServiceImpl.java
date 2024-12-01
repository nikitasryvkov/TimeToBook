package com.web.time_to_book.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.dtos.response.UserResponseDTO;
import com.web.time_to_book.exceptions.role.RoleNotFoundException;
import com.web.time_to_book.exceptions.user.InvalidUserDataException;
import com.web.time_to_book.exceptions.user.UserNotFoundException;
import com.web.time_to_book.models.Role;
import com.web.time_to_book.models.User;
import com.web.time_to_book.repositories.RoleRepository;
import com.web.time_to_book.repositories.UserRepository;
import com.web.time_to_book.services.UserService;
import com.web.time_to_book.utils.validation.ValidationUtil;

import jakarta.validation.ConstraintViolation;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setUserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public void addUser(UserRequestDTO userDTO) {
        if (!this.validationUtil.isValid(userDTO)) {
            this.validationUtil
                    .violations(userDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidUserDataException();
        }

        Role role = roleRepository.findByName("USER").orElseThrow(
                () -> new RoleNotFoundException(userDTO.getFirstName()));
        User user = modelMapper.map(userDTO, User.class);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UUID id, UserRequestDTO userDTO) {
        if (!this.validationUtil.isValid(userDTO)) {
            this.validationUtil
                    .violations(userDTO)
                    .stream().map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidUserDataException();
        }

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAvatarURL(userDTO.getAvatarURL());

        userRepository.update(user);
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> modelMapper.map(users, UserResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        return modelMapper.map(user, UserResponseDTO.class);
    }
}
