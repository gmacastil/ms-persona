package com.lite.ms_persona.domain.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelefonoColombianoValidator.class)
public @interface TelefonoColombiano {

    String message() default "El teléfono debe ser un número celular colombiano válido (ej: 3001234567)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
