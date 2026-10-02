package com.lite.ms_persona.domain;


public record Persona (
    String nombre,
    String apellido,
    String email,
    String telefono,
    String id
) 

{
    public Persona(String nombre, String apellido, String email, String telefono) {
        this(nombre, apellido, email, telefono, null);
    }
}