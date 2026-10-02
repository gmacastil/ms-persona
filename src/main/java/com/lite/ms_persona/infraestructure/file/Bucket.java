package com.lite.ms_persona.infraestructure.file;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.lite.ms_persona.domain.Persona;
import com.lite.ms_persona.domain.port.PersonaRepository;

/**
 * Adaptador de salida (driven adapter) que implementa el puerto
 * {@link PersonaRepository}. Provee una persistencia en memoria a modo
 * de "bucket" de personas, pudiendo ser reemplazado por otra tecnología
 * (base de datos, archivo, etc.) sin afectar el dominio ni la aplicación.
 */
@Repository
public class Bucket implements PersonaRepository {

    private final Map<String, Persona> personas = new ConcurrentHashMap<>();

    @Override
    public Persona save(Persona persona) {
        personas.put(persona.id(), persona);
        return persona;
    }

    @Override
    public Optional<Persona> findById(String id) {
        return Optional.ofNullable(personas.get(id));
    }

    @Override
    public List<Persona> findAll() {
        return List.copyOf(personas.values());
    }

    @Override
    public boolean existsById(String id) {
        return personas.containsKey(id);
    }

    @Override
    public void deleteById(String id) {
        personas.remove(id);
    }
}
