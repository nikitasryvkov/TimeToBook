package com.web.time_to_book.services;

import java.util.List;
import java.util.UUID;

import com.web.time_to_book.dtos.CategoryDTO;

public interface CategoryService {
    void addCategory(CategoryDTO userDTO);

    void updateCategory(UUID id, CategoryDTO categoryDTO);

    List<CategoryDTO> findAllCategories();

    CategoryDTO findById(UUID id);
}
