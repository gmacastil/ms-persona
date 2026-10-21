package com.lite.ms_persona.application.command;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import org.springframework.stereotype.Component;

import com.lite.ms_persona.application.bus.CommandHandler;
import com.lite.ms_persona.application.bus.EventBus;
import com.lite.ms_persona.domain.Persona;
import com.lite.ms_persona.domain.PersonaRepository;
import com.lite.ms_persona.domain.eventos.PersonaCreada;

@Component
public class CrearPersonaCommandHandler implements CommandHandler<CrearPersonaCommand, Persona> {

    private final PersonaRepository personaRepository;
    private final Validator validator;
    private final EventBus eventBus;

    public CrearPersonaCommandHandler(PersonaRepository personaRepository, Validator validator, EventBus eventBus) {
        this.personaRepository = personaRepository;
        this.validator = validator;
        this.eventBus = eventBus;
    }

    @Override
    public Class<CrearPersonaCommand> commandType() {
        return CrearPersonaCommand.class;
    }

    @Override
    public Persona handle(CrearPersonaCommand command) {
        Persona persona = command.persona();
        Persona nuevaPersona = new Persona(
                persona.nombre(), persona.apellido(), persona.email(), persona.telefono(),
                UUID.randomUUID().toString());
        validar(nuevaPersona);
        Persona personaCreada = personaRepository.save(nuevaPersona);
        eventBus.publish(new PersonaCreada(personaCreada));
        return personaCreada;
    }

    private void validar(Persona persona) {
        Set<ConstraintViolation<Persona>> violaciones = validator.validate(persona);
        if (!violaciones.isEmpty()) {
            throw new ConstraintViolationException(violaciones);
        }
    }
}
