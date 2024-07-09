package com.project.oriobook.common.annotations.validator;

import com.project.oriobook.common.annotations.UUIDValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UUIDValidator implements ConstraintValidator<UUIDValid, String> {
    private static final Pattern UUID_PATTERN =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull should handle null check
        }
        return UUID_PATTERN.matcher(value).matches();
    }
}

