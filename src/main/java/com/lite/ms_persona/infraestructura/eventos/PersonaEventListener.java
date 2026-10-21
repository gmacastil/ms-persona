package com.lite.ms_persona.infraestructura.eventos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.lite.ms_persona.domain.eventos.PersonaActualizada;
import com.lite.ms_persona.domain.eventos.PersonaCreada;
import com.lite.ms_persona.domain.eventos.PersonaEliminada;

import static net.logstash.logback.argument.StructuredArguments.value;

/**
 * Consumidor de eventos de dominio de {@code Persona}, desacoplado del
 * flujo de comandos que los origina.
 */
@Component
public class PersonaEventListener {

    private static final Logger log = LoggerFactory.getLogger(PersonaEventListener.class);

    @EventListener
    public void onPersonaCreada(PersonaCreada evento) {
        log.info("Persona creada", value("id", evento.persona().id()));
    }

    @EventListener
    public void onPersonaActualizada(PersonaActualizada evento) {
        log.info("Persona actualizada", value("id", evento.persona().id()));
    }

    @EventListener
    public void onPersonaEliminada(PersonaEliminada evento) {
        log.info("Persona eliminada", value("id", evento.id()));
    }
}
