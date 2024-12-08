package com.web.time_to_book.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.time_to_book.dtos.request.FeedbackRequestDTO;
import com.web.time_to_book.dtos.response.FeedbackDTO;
import com.web.time_to_book.dtos.response.FeedbackResponseDTO;
import com.web.time_to_book.exceptions.feedback.FeedbackNotFoundException;
import com.web.time_to_book.exceptions.feedback.InvalidFeedbackDataException;
import com.web.time_to_book.exceptions.serviceProduct.ServiceProductNotFoundException;
import com.web.time_to_book.exceptions.user.UserNotFoundException;
import com.web.time_to_book.models.Feedback;
import com.web.time_to_book.models.ServiceProduct;
import com.web.time_to_book.models.User;
import com.web.time_to_book.repositories.FeedbackRepository;
import com.web.time_to_book.repositories.ServiceProductRepository;
import com.web.time_to_book.repositories.UserRepository;
import com.web.time_to_book.services.FeedbackService;
import com.web.time_to_book.utils.validation.ValidationUtil;

import jakarta.validation.ConstraintViolation;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackRepository feedbackRepository;
    private ServiceProductRepository serviceProductRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public FeedbackServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setFeedbackServiceImpl(FeedbackRepository feedbackRepository,
            ServiceProductRepository serviceProductRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.feedbackRepository = feedbackRepository;
        this.serviceProductRepository = serviceProductRepository;
        this.userRepository = userRepository;
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

        ServiceProduct serviceProduct = serviceProductRepository.findById(feedbackDTO.getServiceId())
                .orElseThrow(() -> new ServiceProductNotFoundException(feedbackDTO.getServiceId()));
        User user = userRepository.findById(feedbackDTO.getCreatedById())
                .orElseThrow(() -> new UserNotFoundException(feedbackDTO.getCreatedById()));
        Feedback feedback = new Feedback(
                feedbackDTO.getText(),
                serviceProduct,
                user,
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
        ServiceProduct serviceProduct = serviceProductRepository.findById(feedbackDTO.getServiceId())
                .orElseThrow(() -> new ServiceProductNotFoundException(feedbackDTO.getServiceId()));
        feedback.setService(serviceProduct);
        User user = userRepository.findById(feedbackDTO.getCreatedById())
                .orElseThrow(() -> new UserNotFoundException(feedbackDTO.getCreatedById()));
        feedback.setCreatedBy(user);
        feedback.setEstimation(feedbackDTO.getEstimation());

        feedbackRepository.update(feedback);
    }

    @Override
    public List<FeedbackResponseDTO> findAllFeedbacks() {
        return feedbackRepository.findAll().stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackDTO findById(UUID id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new FeedbackNotFoundException(id));

        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    @Override
    public List<FeedbackResponseDTO> findAllFeedbacksById(UUID id) {
        return feedbackRepository.findAllBy(id).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackResponseDTO.class)).collect(Collectors.toList());
    }

}
