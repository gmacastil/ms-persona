package com.lite.ms_persona.domain.port;

import java.util.List;
import java.util.Optional;

import com.lite.ms_persona.domain.Persona;

/**
 * Puerto de salida (output port) definido en el dominio.
 * Los adaptadores de infraestructura implementan esta interfaz
 * para proveer la persistencia de {@link Persona}.
 */
public interface PersonaRepository {

    Persona save(Persona persona);

    Optional<Persona> findById(String id);

    List<Persona> findAll();

    boolean existsById(String id);

    void deleteById(String id);
}
