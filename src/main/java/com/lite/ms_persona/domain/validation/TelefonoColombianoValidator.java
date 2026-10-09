package com.lite.ms_persona.domain.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefonoColombianoValidator implements ConstraintValidator<TelefonoColombiano, String> {

    private static final Pattern CELULAR_COLOMBIANO = Pattern.compile("^(?:\\+?57)?3\\d{9}$");

    @Override
    public boolean isValid(String telefono, ConstraintValidatorContext context) {
        if (telefono == null || telefono.isBlank()) {
            return true;
        }
        String normalizado = telefono.replaceAll("[\\s()-]", "");
        return CELULAR_COLOMBIANO.matcher(normalizado).matches();
    }
}
