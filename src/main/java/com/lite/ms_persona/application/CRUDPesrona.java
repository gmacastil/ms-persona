package com.lite.ms_persona.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lite.ms_persona.application.port.PersonaService;
import com.lite.ms_persona.domain.Persona;
import com.lite.ms_persona.domain.port.PersonaRepository;

/**
 * Caso de uso que contiene la lógica de negocio del CRUD de {@link Persona}.
 * Implementa el puerto de entrada {@link PersonaService} y depende únicamente
 * del puerto de salida {@link PersonaRepository}, sin conocer detalles de
 * infraestructura (arquitectura hexagonal).
 */
@Service
public class CRUDPesrona implements PersonaService {

    private final PersonaRepository personaRepository;

    public CRUDPesrona(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Persona createPersona(Persona persona) {
        validarDatosObligatorios(persona);
        Persona nuevaPersona = new Persona(
                persona.nombre(),
                persona.apellido(),
                persona.email(),
                persona.telefono(),
                UUID.randomUUID().toString());
        return personaRepository.save(nuevaPersona);
    }

    @Override
    public Persona readPersona(String id) {
        return personaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe una persona con id: " + id));
    }

    @Override
    public List<Persona> readAllPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public Persona updatePersona(String id, Persona persona) {
        if (!personaRepository.existsById(id)) {
            throw new NoSuchElementException("No existe una persona con id: " + id);
        }
        validarDatosObligatorios(persona);
        Persona personaActualizada = new Persona(
                persona.nombre(),
                persona.apellido(),
                persona.email(),
                persona.telefono(),
                id);
        return personaRepository.save(personaActualizada);
    }

    @Override
    public void deletePersona(String id) {
        if (!personaRepository.existsById(id)) {
            throw new NoSuchElementException("No existe una persona con id: " + id);
        }
        personaRepository.deleteById(id);
    }

    private void validarDatosObligatorios(Persona persona) {
        Optional.ofNullable(persona).orElseThrow(() -> new IllegalArgumentException("La persona no puede ser nula"));
        if (Objects.isNull(persona.nombre()) || persona.nombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la persona es obligatorio");
        }
        if (Objects.isNull(persona.apellido()) || persona.apellido().isBlank()) {
            throw new IllegalArgumentException("El apellido de la persona es obligatorio");
        }
        if (Objects.isNull(persona.email()) || persona.email().isBlank()) {
            throw new IllegalArgumentException("El email de la persona es obligatorio");
        }
    }
}
