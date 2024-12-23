package com.web.time_to_book.utils.validation.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.web.time_to_book.repositories.UserRepository;
import com.web.time_to_book.utils.validation.UniqueEmailValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidatorImpl implements ConstraintValidator<UniqueEmailValidator, String> {

    private UserRepository usersRepository;

    public UniqueEmailValidatorImpl() {
    }

    @Autowired
    public void setUsersRepository(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        return usersRepository.findByEmail(email).isEmpty();
    }
}
