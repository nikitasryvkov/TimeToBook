package com.web.time_to_book.services;

import java.util.List;
import java.util.UUID;

import com.web.time_to_book.dtos.request.FeedbackRequestDTO;
import com.web.time_to_book.dtos.response.FeedbackDTO;

public interface FeedbackService {
    void addFeedback(FeedbackRequestDTO feedbackDTO);

    void updateFeedback(UUID id, FeedbackRequestDTO feedbackDTO);

    List<FeedbackDTO> findAllFeedbacks();

    List<FeedbackDTO> findAllFeedbacksByUserId(UUID id);

    List<FeedbackDTO> findAllFeedbacksByMasterId(UUID id);

    List<FeedbackDTO> findAllFeedbackByServiceId(UUID id);

    FeedbackDTO findById(UUID id);
}
