package com.web.time_to_book.utils.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.web.time_to_book.utils.validation.impl.UniqueEmailValidatorImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueEmailValidatorImpl.class)
public @interface UniqueEmailValidator {
    String message() default "User already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
