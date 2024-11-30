package com.web.time_to_book.utils.validation.impl;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.web.time_to_book.utils.validation.ValidationUtil;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E object) {
        return this.validator.validate(object).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E object) {
        return this.validator.validate(object);
    }

}
