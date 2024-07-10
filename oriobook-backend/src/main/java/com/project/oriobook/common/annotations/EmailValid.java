package com.project.oriobook.common.annotations;

import com.project.oriobook.common.annotations.validator.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValid {
    String message() default "email format is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
