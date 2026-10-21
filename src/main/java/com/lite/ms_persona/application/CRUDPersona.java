package com.lite.ms_persona.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import org.springframework.stereotype.Service;

import com.lite.ms_persona.domain.Persona;
import com.lite.ms_persona.domain.PersonaRepository;

/**
 * Caso de uso que contiene la lógica de negocio del CRUD de {@link Persona}.
 * Implementa el puerto de entrada {@link PersonaService} y depende únicamente
 * del puerto de salida {@link PersonaRepository}, sin conocer detalles de
 * infraestructura (arquitectura hexagonal).
 */
@Service
public class CRUDPersona implements PersonaService {

    private final PersonaRepository personaRepository;
    private final Validator validator;

    public CRUDPersona(PersonaRepository personaRepository, Validator validator) {
        this.personaRepository = personaRepository;
        this.validator = validator;
    }

    @Override
    public Persona createPersona(Persona persona) {
        Persona nuevaPersona = new Persona(
                persona.nombre(), persona.apellido(), persona.email(), persona.telefono(),
                UUID.randomUUID().toString());
        validar(nuevaPersona);
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
        exigirQueExista(id);
        Persona personaActualizada = new Persona(persona.nombre(), persona.apellido(), persona.email(),
                persona.telefono(), id);
        validar(personaActualizada);
        return personaRepository.save(personaActualizada);
    }

    @Override
    public void deletePersona(String id) {
        exigirQueExista(id);
        personaRepository.deleteById(id);
    }

    private void exigirQueExista(String id) {
        if (!personaRepository.existsById(id)) {
            throw new NoSuchElementException("No existe una persona con id: " + id);
        }
    }

    private void validar(Persona persona) {
        Set<ConstraintViolation<Persona>> violaciones = validator.validate(persona);
        if (!violaciones.isEmpty()) {
            throw new ConstraintViolationException(violaciones);
        }
    }
}
