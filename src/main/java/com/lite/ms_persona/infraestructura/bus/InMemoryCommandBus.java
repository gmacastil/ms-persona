package com.lite.ms_persona.infraestructura.bus;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.lite.ms_persona.application.bus.Command;
import com.lite.ms_persona.application.bus.CommandBus;
import com.lite.ms_persona.application.bus.CommandHandler;

/**
 * Adaptador del {@link CommandBus} que despacha cada {@link Command} al
 * {@link CommandHandler} registrado para su tipo, separando el tráfico de
 * comandos del de eventos.
 */
@Component
public class InMemoryCommandBus implements CommandBus {

    private final Map<Class<? extends Command>, CommandHandler<Command, Object>> handlersPorTipo;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public InMemoryCommandBus(List<CommandHandler> handlers) {
        this.handlersPorTipo = handlers.stream()
                .collect(Collectors.toMap(
                        CommandHandler::commandType,
                        (Function<CommandHandler, CommandHandler<Command, Object>>) handler -> handler));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R dispatch(Command command) {
        CommandHandler<Command, Object> handler = handlersPorTipo.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException("No hay handler registrado para el comando: " + command.getClass());
        }
        return (R) handler.handle(command);
    }
}
