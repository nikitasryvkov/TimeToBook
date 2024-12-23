package com.web.time_to_book.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.dtos.response.UserResponseDTO;
import com.web.time_to_book.exceptions.role.RoleNotFoundException;
import com.web.time_to_book.exceptions.user.InvalidUserDataException;
import com.web.time_to_book.exceptions.user.UserNotFoundException;
import com.web.time_to_book.models.User;
import com.web.time_to_book.models.enums.UserRoles;
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
    private PasswordEncoder passwordEncoder;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setUserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAvatarURL(userDTO.getAvatarURL());

        return modelMapper.map(userRepository.update(user), UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public void register(UserRequestDTO userRegistrationDto) {
        if (!this.validationUtil.isValid(userRegistrationDto)) {
            this.validationUtil
                    .violations(userRegistrationDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidUserDataException();
        }
        var userRole = roleRepository.findByName(UserRoles.USER).orElseThrow(() -> new RoleNotFoundException("USER"));
        User user = modelMapper.map(userRegistrationDto, User.class);
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRoles(List.of(userRole));
        userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " не найден!"));
    }

    @Override
    public UUID findIdByUsername(String name) {
        return userRepository.findIdByUsername(name).orElseThrow(() -> new UserNotFoundException(name));
    }

    @Override
    public UserResponseDTO findByUsername(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException(name));

        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> findAllMasters() {
        return userRepository.findAllMasters().stream().map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer countAppointment(UUID id) {
        return userRepository.countAppointment(id);
    }
}
