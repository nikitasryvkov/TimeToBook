package com.web.time_to_book.utils.mapper;

import org.modelmapper.ModelMapper;

import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.dtos.response.UserResponseDTO;
import com.web.time_to_book.models.Feedback;
import com.web.time_to_book.models.User;

public class ModelMapperFactory {

    private ModelMapper modelMapper;

    public ModelMapper getMapper() {
        if (modelMapper == null) {
            modelMapper = initializeModelMapper(new ModelMapper());
        }

        return modelMapper;
    }

    private ModelMapper initializeModelMapper(ModelMapper modelMapper) {

        modelMapper
                .typeMap(User.class, UserRequestDTO.class)
                .addMappings(mapper -> {
                    mapper.map(user -> user.getFirstName(), UserRequestDTO::setFirstName);
                    mapper.map(user -> user.getLastName(), UserRequestDTO::setLastName);
                    mapper.map(user -> user.getUsername(), UserRequestDTO::setUsername);
                    mapper.map(user -> user.getEmail(), UserRequestDTO::setEmail);
                    mapper.map(user -> user.getPassword(), UserRequestDTO::setPassword);
                    mapper.map(user -> user.getPhoneNumber(), UserRequestDTO::setPhoneNumber);
                    mapper.map(user -> user.getAvatarURL(), UserRequestDTO::setAvatarURL);
                    mapper.map(user -> user.getPassword(), UserRequestDTO::setPassword);
                    mapper.map(user -> user.getRole().getName(), UserRequestDTO::setRoleName);
                });

        modelMapper
                .typeMap(User.class, UserResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(user -> user.getFirstName(), UserResponseDTO::setFirstName);
                    mapper.map(user -> user.getLastName(), UserResponseDTO::setLastName);
                    mapper.map(user -> user.getUsername(), UserResponseDTO::setUsername);
                    mapper.map(user -> user.getEmail(), UserResponseDTO::setEmail);
                    mapper.map(user -> user.getPassword(), UserResponseDTO::setPassword);
                    mapper.map(user -> user.getPhoneNumber(), UserResponseDTO::setPhoneNumber);
                    mapper.map(user -> user.getAvatarURL(), UserResponseDTO::setAvatarURL);
                    mapper.map(user -> user.getPassword(), UserResponseDTO::setPassword);
                    mapper.map(user -> user.getNumberOfAppointments(), UserResponseDTO::setNumberOfAppointments);
                });

        return modelMapper;
    }
}
