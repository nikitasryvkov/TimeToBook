package com.web.time_to_book.services;

import java.util.List;
import java.util.UUID;

import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.dtos.response.UserResponseDTO;

public interface UserService {
    void addUser(UserRequestDTO userDTO);

    void updateUser(UUID id, UserRequestDTO userDTO);

    List<UserResponseDTO> findAllUsers();

    UserResponseDTO findById(UUID id);

    UUID verify(String name);
}
