package com.lite.ms_persona.application.command;

import com.lite.ms_persona.application.bus.Command;

public record EliminarPersonaCommand(String id) implements Command {
}
