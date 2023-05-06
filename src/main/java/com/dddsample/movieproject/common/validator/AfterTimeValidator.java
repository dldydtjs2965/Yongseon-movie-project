package com.dddsample.movieproject.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class AfterTimeValidator implements ConstraintValidator<AfterTime, LocalTime> {
    private LocalTime time;

    public void initialize(AfterTime annotation) {
        time = LocalTime.parse(annotation.value());
    }

    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        boolean valid = true;
        if (value != null) {
            if (!value.isAfter(time)) {
                valid = false;
            }
        }
        return valid;
    }
}
