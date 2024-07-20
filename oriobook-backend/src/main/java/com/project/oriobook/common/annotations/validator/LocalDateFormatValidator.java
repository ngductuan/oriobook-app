package com.project.oriobook.common.annotations.validator;

import com.project.oriobook.common.annotations.ValidDateFormat;
import com.project.oriobook.common.constants.CommonConst;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateFormatValidator implements ConstraintValidator<ValidDateFormat, LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConst.DATE_DTO_FORMAT);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull should handle null check
        }
        try {
            String formattedDate = value.format(formatter);
            LocalDate.parse(formattedDate, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
        // return true;
    }
}

