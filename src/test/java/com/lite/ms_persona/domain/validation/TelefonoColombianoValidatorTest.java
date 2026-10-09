package com.lite.ms_persona.domain.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class TelefonoColombianoValidatorTest {

    private final TelefonoColombianoValidator validator = new TelefonoColombianoValidator();

    @ParameterizedTest
    @ValueSource(strings = { "3001234567", "+573001234567", "573001234567", "300 123 4567", "300-123-4567" })
    void acceptsValidColombianMobileNumbers(String telefono) {
        assertTrue(validator.isValid(telefono, null));
    }

    @ParameterizedTest
    @CsvSource({
            "300123456",
            "4001234567",
            "abcdefghij",
            "+5730012345",
            "30012345678"
    })
    void rejectsInvalidColombianMobileNumbers(String telefono) {
        assertFalse(validator.isValid(telefono, null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void treatsNullOrBlankAsValid(String telefono) {
        assertTrue(validator.isValid(telefono, null));
    }

    @Test
    void treatsBlankWithSpacesAsValid() {
        assertTrue(validator.isValid("   ", null));
    }
}
