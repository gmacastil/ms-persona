package com.lite.ms_persona.application.bus;

/**
 * Puerto de entrada para despachar comandos del lado de escritura,
 * independiente del transporte (REST, mensajería, etc.).
 */
public interface CommandBus {

    <R> R dispatch(Command command);
}
