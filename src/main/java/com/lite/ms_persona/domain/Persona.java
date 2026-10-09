package com.lite.ms_persona.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import com.lite.ms_persona.domain.validation.TelefonoColombiano;

public record Persona (
    @NotBlank
    String nombre,
    @NotBlank
    String apellido,
    @NotBlank
    @Email
    String email,
    @TelefonoColombiano
    String telefono,
    String id
)

{
    public Persona(String nombre, String apellido, String email, String telefono) {
        this(nombre, apellido, email, telefono, null);
    }
}