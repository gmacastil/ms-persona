package com.lite.ms_persona.application.command;

import java.util.NoSuchElementException;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import org.springframework.stereotype.Component;

import com.lite.ms_persona.application.bus.CommandHandler;
import com.lite.ms_persona.application.bus.EventBus;
import com.lite.ms_persona.domain.Persona;
import com.lite.ms_persona.domain.PersonaRepository;
import com.lite.ms_persona.domain.eventos.PersonaActualizada;

@Component
public class ActualizarPersonaCommandHandler implements CommandHandler<ActualizarPersonaCommand, Persona> {

    private final PersonaRepository personaRepository;
    private final Validator validator;
    private final EventBus eventBus;

    public ActualizarPersonaCommandHandler(PersonaRepository personaRepository, Validator validator,
            EventBus eventBus) {
        this.personaRepository = personaRepository;
        this.validator = validator;
        this.eventBus = eventBus;
    }

    @Override
    public Class<ActualizarPersonaCommand> commandType() {
        return ActualizarPersonaCommand.class;
    }

    @Override
    public Persona handle(ActualizarPersonaCommand command) {
        String id = command.id();
        if (!personaRepository.existsById(id)) {
            throw new NoSuchElementException("No existe una persona con id: " + id);
        }
        Persona persona = command.persona();
        Persona personaActualizada = new Persona(persona.nombre(), persona.apellido(), persona.email(),
                persona.telefono(), id);
        validar(personaActualizada);
        Persona personaGuardada = personaRepository.save(personaActualizada);
        eventBus.publish(new PersonaActualizada(personaGuardada));
        return personaGuardada;
    }

    private void validar(Persona persona) {
        Set<ConstraintViolation<Persona>> violaciones = validator.validate(persona);
        if (!violaciones.isEmpty()) {
            throw new ConstraintViolationException(violaciones);
        }
    }
}
