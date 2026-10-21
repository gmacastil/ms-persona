package com.lite.ms_persona.infraestructura.rest;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lite.ms_persona.application.PersonaService;
import com.lite.ms_persona.domain.Persona;

/**
 * Adaptador de entrada (driving adapter) que expone los casos de uso de
 * {@link PersonaService} mediante una API REST. No contiene lógica de
 * negocio, solo delega en el servicio de aplicación.
 */
@RestController
@RequestMapping("/v2/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping
    public ResponseEntity<Persona> createPersona(@Valid @RequestBody Persona persona) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personaService.createPersona(persona));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> readPersona(@PathVariable String id) {
        return ResponseEntity.ok(personaService.readPersona(id));
    }

    @GetMapping
    public ResponseEntity<List<Persona>> readAllPersonas() {
        return ResponseEntity.ok(personaService.readAllPersonas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable String id, @Valid @RequestBody Persona persona) {
        return ResponseEntity.ok(personaService.updatePersona(id, persona));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable String id) {
        personaService.deletePersona(id);
        return ResponseEntity.noContent().build();
    }
}
