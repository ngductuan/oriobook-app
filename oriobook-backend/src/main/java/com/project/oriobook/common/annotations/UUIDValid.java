package com.project.oriobook.common.annotations;

import com.project.oriobook.common.annotations.validator.UUIDValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UUIDValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UUIDValid {
    String message() default "invalid UUID format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}