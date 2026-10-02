package com.lite.ms_persona.application.port;

import java.util.List;

import com.lite.ms_persona.domain.Persona;

/**
 * Puerto de entrada (input port) que expone los casos de uso
 * de negocio para {@link Persona}. El controller de la capa de
 * infraestructura depende de esta interfaz, no de la implementación.
 */
public interface PersonaService {

    Persona createPersona(Persona persona);

    Persona readPersona(String id);

    List<Persona> readAllPersonas();

    Persona updatePersona(String id, Persona persona);

    void deletePersona(String id);
}
