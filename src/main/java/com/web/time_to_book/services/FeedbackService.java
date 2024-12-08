package com.web.time_to_book.services;

import java.util.List;
import java.util.UUID;

import com.web.time_to_book.dtos.request.FeedbackRequestDTO;
import com.web.time_to_book.dtos.response.FeedbackDTO;
import com.web.time_to_book.dtos.response.FeedbackResponseDTO;

public interface FeedbackService {
    void addFeedback(FeedbackRequestDTO feedbackDTO);

    void updateFeedback(UUID id, FeedbackRequestDTO feedbackDTO);

    List<FeedbackResponseDTO> findAllFeedbacks();

    List<FeedbackResponseDTO> findAllFeedbacksById(UUID id);

    FeedbackDTO findById(UUID id);
}
