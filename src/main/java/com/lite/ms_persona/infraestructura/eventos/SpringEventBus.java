package com.lite.ms_persona.infraestructura.eventos;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.lite.ms_persona.application.bus.EventBus;
import com.lite.ms_persona.domain.eventos.PersonaEvento;

/**
 * Adaptador del {@link EventBus} sobre el publicador de eventos de Spring,
 * que mantiene el tráfico de eventos separado del de comandos.
 */
@Component
public class SpringEventBus implements EventBus {

    private final ApplicationEventPublisher publisher;

    public SpringEventBus(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(PersonaEvento evento) {
        publisher.publishEvent(evento);
    }
}
