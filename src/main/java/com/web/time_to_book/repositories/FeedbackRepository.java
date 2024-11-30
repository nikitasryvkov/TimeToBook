package com.web.time_to_book.repositories;

import java.util.Optional;
import java.util.UUID;

import com.web.time_to_book.models.Feedback;

public interface FeedbackRepository {
    Optional<Feedback> findById(UUID id);

    Feedback save(Feedback service);

    Feedback update(Feedback service);
}
