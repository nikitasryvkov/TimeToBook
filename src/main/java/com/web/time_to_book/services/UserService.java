package com.web.time_to_book.services;

import java.util.List;
import java.util.UUID;

import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.dtos.response.UserResponseDTO;
import com.web.time_to_book.models.User;

public interface UserService {
    void addUser(UserRequestDTO userDTO);

    UserResponseDTO updateUser(UUID id, UserRequestDTO userDTO);

    List<UserResponseDTO> findAllUsers();

    UserResponseDTO findById(UUID id);

    void register(UserRequestDTO user);

    User getUser(String username);

    UUID findIdByUsername(String name);

    UserResponseDTO findByUsername(String name);

    List<UserResponseDTO> findAllMasters();

    Integer countAppointment(UUID id);
}
