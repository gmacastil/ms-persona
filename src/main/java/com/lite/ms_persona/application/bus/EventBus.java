package com.lite.ms_persona.application.bus;

import com.lite.ms_persona.domain.eventos.PersonaEvento;

/**
 * Puerto de salida para publicar eventos de dominio, separado del
 * {@link CommandBus} para diferenciar el tráfico de escritura (comandos)
 * del de notificación (eventos).
 */
public interface EventBus {

    void publish(PersonaEvento evento);
}
