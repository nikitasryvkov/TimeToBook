package com.web.time_to_book.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.time_to_book.dtos.request.AppointmentRequestDTO;
import com.web.time_to_book.dtos.response.AppointmentDTO;
import com.web.time_to_book.exceptions.appointment.AppointmentNotFoundException;
import com.web.time_to_book.exceptions.appointment.InvalidAppointmentDataException;
import com.web.time_to_book.exceptions.serviceProduct.ServiceProductNotFoundException;
import com.web.time_to_book.exceptions.user.UserNotFoundException;
import com.web.time_to_book.models.Appointment;
import com.web.time_to_book.models.ServiceProduct;
import com.web.time_to_book.models.User;
import com.web.time_to_book.models.enums.AppointmentStatusEnum;
import com.web.time_to_book.repositories.AppointmentRepository;
import com.web.time_to_book.repositories.ServiceProductRepository;
import com.web.time_to_book.repositories.UserRepository;
import com.web.time_to_book.services.AppointmentService;
import com.web.time_to_book.services.UserService;
import com.web.time_to_book.utils.validation.ValidationUtil;

import jakarta.validation.ConstraintViolation;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    private ServiceProductRepository serviceProductRepository;
    private ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private UserService userService;

    public AppointmentServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setAppointmentServiceImpl(AppointmentRepository appointmentRepository,
            UserRepository userRepository, ServiceProductRepository serviceProductRepository, ModelMapper modelMapper,
            UserService userService) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.serviceProductRepository = serviceProductRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void addAppointment(AppointmentRequestDTO appointmentDTO) {
        if (!this.validationUtil.isValid(appointmentDTO)) {
            this.validationUtil
                    .violations(appointmentDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidAppointmentDataException();
        }

        User client = userRepository.findById(appointmentDTO.getClientId())
                .orElseThrow(() -> new UserNotFoundException(appointmentDTO.getClientId()));
        User master = userRepository.findById(appointmentDTO.getMasterId())
                .orElseThrow(() -> new UserNotFoundException(appointmentDTO.getMasterId()));
        ServiceProduct service = serviceProductRepository.findById(appointmentDTO.getServiceId())
                .orElseThrow(() -> new ServiceProductNotFoundException(appointmentDTO.getServiceId()));

        Appointment appointment = new Appointment(
                appointmentDTO.getRecordTime(),
                client,
                master,
                service);

        appointmentRepository.save(appointment);
    }

    @Override
    public void updateAppointment(UUID id, AppointmentRequestDTO appointmentDTO) {
        if (!this.validationUtil.isValid(appointmentDTO)) {
            this.validationUtil
                    .violations(appointmentDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidAppointmentDataException();
        }

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        if (appointment.getRecordTime() == appointmentDTO.getRecordTime()) {
            appointment.setRecordTime(appointment.getRecordTime());
        } else {
            appointment.setRecordTime(appointmentDTO.getRecordTime());
        }

        User client = userRepository.findById(appointmentDTO.getClientId())
                .orElseThrow(() -> new UserNotFoundException(appointmentDTO.getClientId()));
        if (appointment.getClient().getId() == appointmentDTO.getClientId()) {
            appointment.setClient(appointment.getClient());
        } else {
            appointment.setClient(client);
        }

        User master = userRepository.findById(appointmentDTO.getMasterId())
                .orElseThrow(() -> new UserNotFoundException(appointmentDTO.getMasterId()));
        if (appointment.getMaster().getId() == appointmentDTO.getMasterId()) {
            appointment.setMaster(appointment.getMaster());
        } else {
            appointment.setMaster(master);
        }

        ServiceProduct service = serviceProductRepository.findById(appointmentDTO.getServiceId())
                .orElseThrow(() -> new ServiceProductNotFoundException(appointmentDTO.getServiceId()));
        if (appointment.getService().getId() == appointmentDTO.getServiceId()) {
            appointment.setService(appointment.getService());
        } else {
            appointment.setService(service);
        }

        if (appointment.getStatus().toString() == appointmentDTO.getStatus()) {
            appointment.setStatus(appointment.getStatus());
        } else {
            appointment.setStatus(AppointmentStatusEnum.valueOf(appointmentDTO.getStatus()));
        }

        appointmentRepository.update(appointment);
    }

    @Override
    public List<AppointmentDTO> findAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO findById(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        return modelMapper.map(appointment, AppointmentDTO.class);
    }

    @Override
    public List<AppointmentDTO> findAllByUserId(UUID id) {
        return appointmentRepository.findAllByUserId(id).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> findAllByMasterId(UUID id) {
        return appointmentRepository.findAllByMasterId(id).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> findAllByUserAndStatus(UUID id, AppointmentStatusEnum status) {
        return appointmentRepository.findAllByUserAndStatus(id, status).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }
}
