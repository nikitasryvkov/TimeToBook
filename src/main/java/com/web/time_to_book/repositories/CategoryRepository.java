package com.web.time_to_book.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.web.time_to_book.models.Category;

public interface CategoryRepository {
    Optional<Category> findById(UUID id);

    Category save(Category category);

    Category update(Category category);

    List<Category> findAll();
}
