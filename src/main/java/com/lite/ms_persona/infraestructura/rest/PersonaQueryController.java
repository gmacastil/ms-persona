package com.lite.ms_persona.infraestructura.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lite.ms_persona.application.query.PersonaQueryService;
import com.lite.ms_persona.domain.Persona;

/**
 * Lado de lectura (query) de CQRS: expone las consultas de {@link Persona}
 * a través de {@link PersonaQueryService}, separado del controller de
 * escritura.
 */
@RestController
@RequestMapping("/v2/personas")
public class PersonaQueryController {

    private final PersonaQueryService personaQueryService;

    public PersonaQueryController(PersonaQueryService personaQueryService) {
        this.personaQueryService = personaQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> readPersona(@PathVariable String id) {
        return ResponseEntity.ok(personaQueryService.porId(id));
    }

    @GetMapping
    public ResponseEntity<List<Persona>> readAllPersonas() {
        return ResponseEntity.ok(personaQueryService.todas());
    }
}
