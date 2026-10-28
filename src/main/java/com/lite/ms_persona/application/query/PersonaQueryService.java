package com.lite.ms_persona.application.query;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lite.ms_persona.domain.Persona;
import com.lite.ms_persona.domain.PersonaRepository;

/**
 * Lado de lectura (query) de CQRS: expone consultas de {@link Persona}
 * independientes del flujo de comandos.
 */
@Service
public class PersonaQueryService {

    public static final String CACHE_NAME = "personas";

    private final PersonaRepository personaRepository;

    public PersonaQueryService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    public Persona porId(String id) {
        return personaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe una persona con id: " + id));
    }

    public List<Persona> todas() {
        return personaRepository.findAll();
    }
}
