package com.web.time_to_book.utils.mapper;

import org.modelmapper.ModelMapper;

import com.example.demo.viewmodel.appointment.AppointmentViewModel;
import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.dtos.response.AppointmentDTO;
import com.web.time_to_book.dtos.response.AppointmentResponseDTO;
import com.web.time_to_book.dtos.response.UserResponseDTO;
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
                    mapper.map(user -> user.getId(), UserRequestDTO::setId);
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

        modelMapper
                .typeMap(AppointmentDTO.class, AppointmentViewModel.class)
                .addMappings(mapper -> {
                    mapper.map(appointment -> appointment.getId(), AppointmentViewModel::setId);
                    mapper.map(appointment -> appointment.getCreatedAt(), AppointmentViewModel::setCreatedAt);
                    mapper.map(appointment -> appointment.getRecordTime(), AppointmentViewModel::setRecordTime);
                    mapper.map(appointment -> appointment.getClientId(), AppointmentViewModel::setClientId);
                    mapper.map(appointment -> appointment.getClientName(), AppointmentViewModel::setClientName);
                    mapper.map(appointment -> appointment.getMasterId(), AppointmentViewModel::setMasterId);
                    mapper.map(appointment -> appointment.getMasterName(), AppointmentViewModel::setMasterName);
                    mapper.map(appointment -> appointment.getServiceId(), AppointmentViewModel::setServiceId);
                    mapper.map(appointment -> appointment.getServiceTitle(), AppointmentViewModel::setServiceTitle);
                    mapper.map(appointment -> appointment.getStatus(), AppointmentViewModel::setStatus);
                });

        return modelMapper;
    }
}
