package com.web.time_to_book.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.time_to_book.dtos.CategoryDTO;
import com.web.time_to_book.exceptions.category.CategoryNotFoundException;
import com.web.time_to_book.exceptions.category.InvalidCategoryDataException;
import com.web.time_to_book.models.Category;
import com.web.time_to_book.models.enums.CategoryStatusEnum;
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

    @Autowired
    public void setCategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        if (!this.validationUtil.isValid(categoryDTO)) {
            this.validationUtil
                    .violations(categoryDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidCategoryDataException();
        }

        Category category = new Category(categoryDTO.getName(), CategoryStatusEnum.PUBLISHED);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(UUID id, CategoryDTO categoryDTO) {
        if (!this.validationUtil.isValid(categoryDTO)) {
            this.validationUtil
                    .violations(categoryDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidCategoryDataException();
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        category.setName(categoryDTO.getName());
        categoryRepository.update(category);
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        return categoryRepository.findAll().stream().map(c -> modelMapper.map(c, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        return modelMapper.map(category, CategoryDTO.class);
    }

}
