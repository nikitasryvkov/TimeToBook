// package com.web.time_to_book.utils.validation;

// import java.lang.annotation.ElementType;
// import java.lang.annotation.Retention;
// import java.lang.annotation.RetentionPolicy;
// import java.lang.annotation.Target;

// import com.web.time_to_book.utils.validation.impl.DataValidatorImpl;

// import jakarta.validation.Constraint;
// import jakarta.validation.Payload;

// @Constraint(validatedBy = DataValidatorImpl.class)
// @Target(ElementType.TYPE)
// @Retention(RetentionPolicy.RUNTIME)
// public @interface DataValidator {
// String message() default "Некорректная дата";

// Class<?>[] groups() default {};

// Class<? extends Payload>[] payload() default {};
// }
