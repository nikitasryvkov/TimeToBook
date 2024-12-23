package com.web.time_to_book.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.time_to_book.dtos.request.FeedbackRequestDTO;
import com.web.time_to_book.dtos.response.FeedbackDTO;
import com.web.time_to_book.exceptions.appointment.AppointmentNotFoundException;
import com.web.time_to_book.exceptions.feedback.FeedbackNotFoundException;
import com.web.time_to_book.exceptions.feedback.InvalidFeedbackDataException;
import com.web.time_to_book.models.Appointment;
import com.web.time_to_book.models.Feedback;
import com.web.time_to_book.repositories.AppointmentRepository;
import com.web.time_to_book.repositories.FeedbackRepository;
import com.web.time_to_book.services.FeedbackService;
import com.web.time_to_book.utils.validation.ValidationUtil;

import jakarta.validation.ConstraintViolation;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackRepository feedbackRepository;
    private AppointmentRepository appointmentRepository;
    private ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public FeedbackServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setFeedbackServiceImpl(FeedbackRepository feedbackRepository,
            AppointmentRepository appointmentRepository, ModelMapper modelMapper) {
        this.feedbackRepository = feedbackRepository;
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addFeedback(FeedbackRequestDTO feedbackDTO) {
        if (!this.validationUtil.isValid(feedbackDTO)) {
            this.validationUtil
                    .violations(feedbackDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidFeedbackDataException();
        }

        Appointment appointment = appointmentRepository.findById(feedbackDTO.getAppointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException(feedbackDTO.getAppointmentId()));
        Feedback feedback = new Feedback(feedbackDTO.getText(), appointment,
                feedbackDTO.getEstimation());

        feedbackRepository.save(feedback);
    }

    @Override
    public void updateFeedback(UUID id, FeedbackRequestDTO feedbackDTO) {
        if (!this.validationUtil.isValid(feedbackDTO)) {
            this.validationUtil
                    .violations(feedbackDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidFeedbackDataException();
        }

        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new FeedbackNotFoundException(id));
        feedback.setText(feedbackDTO.getText());
        Appointment appointment = appointmentRepository.findById(feedbackDTO.getAppointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException(feedbackDTO.getAppointmentId()));
        feedback.setAppointment(appointment);
        feedback.setEstimation(feedbackDTO.getEstimation());

        feedbackRepository.update(feedback);
    }

    @Override
    public List<FeedbackDTO> findAllFeedbacks() {
        return feedbackRepository.findAll().stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackDTO findById(UUID id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new FeedbackNotFoundException(id));

        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    @Override
    public List<FeedbackDTO> findAllFeedbacksByUserId(UUID id) {
        return feedbackRepository.findAllByUserId(id).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> findAllFeedbacksByMasterId(UUID id) {
        return feedbackRepository.findAllByMasterId(id).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> findAllFeedbackByServiceId(UUID id) {
        return feedbackRepository.findAllByServiceId(id).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).collect(Collectors.toList());
    }
}
