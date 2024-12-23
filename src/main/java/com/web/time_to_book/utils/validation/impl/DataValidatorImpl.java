// package com.web.time_to_book.utils.validation.impl;

// import jakarta.validation.ConstraintValidator;
// import jakarta.validation.ConstraintValidatorContext;
// import java.time.LocalDateTime;

// import com.web.time_to_book.models.Appointment;
// import com.web.time_to_book.utils.validation.DataValidator;

// public class DataValidatorImpl implements ConstraintValidator<DataValidator,
// Appointment> {

// @Override
// public boolean isValid(Appointment appointment, ConstraintValidatorContext
// context) {
// if (appointment == null) {
// return true;
// }

// LocalDateTime createdAt = appointment.getCreatedAt();
// LocalDateTime recordTime = appointment.getRecordTime();

// return createdAt != null && recordTime != null &&
// recordTime.isAfter(createdAt);
// }
// }
