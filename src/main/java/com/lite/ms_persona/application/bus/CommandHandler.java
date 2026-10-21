package com.lite.ms_persona.application.bus;

/**
 * Maneja un tipo concreto de {@link Command} y produce un resultado.
 */
public interface CommandHandler<C extends Command, R> {

    Class<C> commandType();

    R handle(C command);
}
