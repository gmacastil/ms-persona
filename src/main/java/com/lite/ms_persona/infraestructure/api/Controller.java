package com.lite.ms_persona.infraestructure.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lite.ms_persona.application.port.PersonaService;
import com.lite.ms_persona.domain.Persona;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.logstash.logback.argument.StructuredArguments.value;

/**
 * Adaptador de entrada (driving adapter) que expone los casos de uso de
 * {@link PersonaService} mediante una API REST. No contiene lógica de
 * negocio, solo delega en el servicio de aplicación.
 */
@RestController
@RequestMapping("/personas")
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private final PersonaService personaService;

    public Controller(PersonaService personaService) {
        this.personaService = personaService;
    }

    static void logError(String method, String path, HttpStatusCode status, Exception ex) {
        log.error("API request failed",
                value("method", method),
                value("path", path),
                value("status", status.value()),
                value("error", ex.getMessage()),
                ex);
    }

    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        Persona personaCreada = personaService.createPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(personaCreada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> readPersona(@PathVariable String id) {
        log.info(null, value("id", id), value("action", "readPersona"), value("status", "success"));
        return ResponseEntity.ok(personaService.readPersona(id));
    }

    @GetMapping
    public ResponseEntity<List<Persona>> readAllPersonas() {
        return ResponseEntity.ok(personaService.readAllPersonas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable String id, @RequestBody Persona persona) {
        return ResponseEntity.ok(personaService.updatePersona(id, persona));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable String id) {
        personaService.deletePersona(id);
        return ResponseEntity.noContent().build();
    }
}
