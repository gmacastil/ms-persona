package com.lite.ms_persona.application.command;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.lite.ms_persona.application.bus.CommandHandler;
import com.lite.ms_persona.application.bus.EventBus;
import com.lite.ms_persona.domain.PersonaRepository;
import com.lite.ms_persona.domain.eventos.PersonaEliminada;

@Component
public class EliminarPersonaCommandHandler implements CommandHandler<EliminarPersonaCommand, Void> {

    private final PersonaRepository personaRepository;
    private final EventBus eventBus;

    public EliminarPersonaCommandHandler(PersonaRepository personaRepository, EventBus eventBus) {
        this.personaRepository = personaRepository;
        this.eventBus = eventBus;
    }

    @Override
    public Class<EliminarPersonaCommand> commandType() {
        return EliminarPersonaCommand.class;
    }

    @Override
    public Void handle(EliminarPersonaCommand command) {
        String id = command.id();
        if (!personaRepository.existsById(id)) {
            throw new NoSuchElementException("No existe una persona con id: " + id);
        }
        personaRepository.deleteById(id);
        eventBus.publish(new PersonaEliminada(id));
        return null;
    }
}
