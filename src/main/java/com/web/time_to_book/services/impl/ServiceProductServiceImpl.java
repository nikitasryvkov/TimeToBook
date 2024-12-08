package com.web.time_to_book.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.time_to_book.dtos.request.ServiceProductRequestDTO;
import com.web.time_to_book.dtos.response.ServiceProductDTO;
import com.web.time_to_book.dtos.response.ServiceProductResponseDTO;
import com.web.time_to_book.exceptions.category.CategoryNotFoundException;
import com.web.time_to_book.exceptions.serviceProduct.InvalidServiceProductDataException;
import com.web.time_to_book.exceptions.serviceProduct.ServiceProductNotFoundException;
import com.web.time_to_book.exceptions.user.UserNotFoundException;
import com.web.time_to_book.models.Category;
import com.web.time_to_book.models.ServiceProduct;
import com.web.time_to_book.models.User;
import com.web.time_to_book.repositories.CategoryRepository;
import com.web.time_to_book.repositories.ServiceProductRepository;
import com.web.time_to_book.repositories.UserRepository;
import com.web.time_to_book.services.ServiceProductService;
import com.web.time_to_book.utils.validation.ValidationUtil;

import jakarta.validation.ConstraintViolation;

@Service
public class ServiceProductServiceImpl implements ServiceProductService {

        private ServiceProductRepository serviceProductRepository;
        private UserRepository userRepository;
        private CategoryRepository categoryRepository;
        private ModelMapper modelMapper;
        private final ValidationUtil validationUtil;

        public ServiceProductServiceImpl(ValidationUtil validationUtil) {
                this.validationUtil = validationUtil;
        }

        @Autowired
        public void setServiceProductServiceImpl(ServiceProductRepository serviceProductRepository,
                        UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
                this.serviceProductRepository = serviceProductRepository;
                this.userRepository = userRepository;
                this.categoryRepository = categoryRepository;
                this.modelMapper = modelMapper;
        }

        @Override
        public void addServiceProduct(ServiceProductRequestDTO serviceProductDTO) {
                if (!this.validationUtil.isValid(serviceProductDTO)) {
                        this.validationUtil
                                        .violations(serviceProductDTO)
                                        .stream()
                                        .map(ConstraintViolation::getMessage)
                                        .forEach(System.out::println);
                        throw new InvalidServiceProductDataException();
                }

                Category category = categoryRepository.findById(serviceProductDTO.getCategoryId())
                                .orElseThrow(() -> new CategoryNotFoundException(serviceProductDTO.getCategoryId()));
                User user = userRepository.findById(serviceProductDTO.getCreatedById())
                                .orElseThrow(() -> new UserNotFoundException(serviceProductDTO.getCreatedById()));
                ServiceProduct serviceProduct = new ServiceProduct(
                                serviceProductDTO.getTitle(),
                                serviceProductDTO.getDescription(),
                                category,
                                serviceProductDTO.getPrice(),
                                user);

                serviceProductRepository.save(serviceProduct);
        }

        @Override
        public void updateServiceProduct(UUID id, ServiceProductRequestDTO serviceProductDTO) {
                if (!this.validationUtil.isValid(serviceProductDTO)) {
                        this.validationUtil
                                        .violations(serviceProductDTO)
                                        .stream()
                                        .map(ConstraintViolation::getMessage)
                                        .forEach(System.out::println);
                        throw new InvalidServiceProductDataException();
                }

                ServiceProduct serviceProduct = serviceProductRepository.findById(id)
                                .orElseThrow(() -> new ServiceProductNotFoundException(id));
                serviceProduct.setTitle(serviceProductDTO.getTitle());
                serviceProduct.setDescription(serviceProductDTO.getDescription());
                Category category = categoryRepository.findById(serviceProductDTO.getCategoryId())
                                .orElseThrow(() -> new CategoryNotFoundException(serviceProductDTO.getCategoryId()));
                serviceProduct.setCategory(category);
                serviceProduct.setPrice(serviceProductDTO.getPrice());
                User user = userRepository.findById(serviceProductDTO.getCreatedById())
                                .orElseThrow(() -> new UserNotFoundException(serviceProductDTO.getCreatedById()));
                serviceProduct.setCreatedBy(user);

                serviceProductRepository.update(serviceProduct);
        }

        @Override
        public List<ServiceProductResponseDTO> findAllServices() {
                return serviceProductRepository.findAll().stream()
                                .map(serviceProduct -> modelMapper.map(serviceProduct, ServiceProductResponseDTO.class))
                                .collect(Collectors.toList());
        }

        @Override
        public ServiceProductDTO findById(UUID id) {
                ServiceProduct serviceProduct = serviceProductRepository.findById(id)
                                .orElseThrow(() -> new ServiceProductNotFoundException(id));

                return modelMapper.map(serviceProduct, ServiceProductDTO.class);
        }

}
