package com.web.time_to_book.services.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.web.time_to_book.dtos.request.CategoryRequestDTO;
import com.web.time_to_book.dtos.response.CategoryResponseDTO;
import com.web.time_to_book.exceptions.category.InvalidCategoryDataException;
import com.web.time_to_book.exceptions.user.InvalidUserDataException;
import com.web.time_to_book.repositories.CategoryRepository;
import com.web.time_to_book.services.CategoryService;
import com.web.time_to_book.utils.validation.ValidationUtil;

import jakarta.validation.ConstraintViolation;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setCategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addCategory(CategoryRequestDTO userDTO) {
        if (!this.validationUtil.isValid(userDTO)) {
            this.validationUtil
                    .violations(userDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidCategoryDataException();
        }
    }

    @Override
    public void updateCategory(UUID id, CategoryRequestDTO userDTO) {
        if (!this.validationUtil.isValid(userDTO)) {
            this.validationUtil
                    .violations(userDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidCategoryDataException();
        }
    }

    @Override
    public List<CategoryResponseDTO> findAllCategories() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllCategories'");
    }

    @Override
    public CategoryResponseDTO findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
