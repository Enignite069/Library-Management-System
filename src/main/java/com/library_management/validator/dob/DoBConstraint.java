package com.library_management.validator.dob;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {DoBValidator.class})
public @interface DoBConstraint {
    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
