package com.web.time_to_book.services;

import java.util.List;
import java.util.UUID;

import com.web.time_to_book.dtos.request.CategoryRequestDTO;
import com.web.time_to_book.dtos.response.CategoryResponseDTO;

public interface CategoryService {
    void addCategory(CategoryRequestDTO userDTO);

    void updateCategory(UUID id, CategoryRequestDTO userDTO);

    List<CategoryResponseDTO> findAllCategories();

    CategoryResponseDTO findById(UUID id);
}
