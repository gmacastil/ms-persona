package com.lite.ms_persona.infraestructura.rest;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lite.ms_persona.application.bus.CommandBus;
import com.lite.ms_persona.application.command.ActualizarPersonaCommand;
import com.lite.ms_persona.application.command.CrearPersonaCommand;
import com.lite.ms_persona.application.command.EliminarPersonaCommand;
import com.lite.ms_persona.domain.Persona;

/**
 * Lado de escritura (command) de CQRS: traduce las peticiones REST en
 * {@link com.lite.ms_persona.application.bus.Command}s y los despacha al
 * {@link CommandBus}, separado del controller de solo lectura.
 */
@RestController
@RequestMapping("/v2/personas")
public class PersonaCommandController {

    private final CommandBus commandBus;

    public PersonaCommandController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping
    public ResponseEntity<Persona> createPersona(@Valid @RequestBody Persona persona) {
        Persona creada = commandBus.dispatch(new CrearPersonaCommand(persona));
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable String id, @Valid @RequestBody Persona persona) {
        Persona actualizada = commandBus.dispatch(new ActualizarPersonaCommand(id, persona));
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable String id) {
        commandBus.dispatch(new EliminarPersonaCommand(id));
        return ResponseEntity.noContent().build();
    }
}
