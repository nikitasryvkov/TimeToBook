package com.web.time_to_book.utils.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.web.time_to_book.utils.validation.impl.UniqueUsernameValidatorImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueUsernameValidatorImpl.class)
public @interface UniqueUsernameValidator {
    String message() default "User already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
