package com.lite.ms_persona.application.command;

import com.lite.ms_persona.application.bus.Command;
import com.lite.ms_persona.domain.Persona;

public record ActualizarPersonaCommand(String id, Persona persona) implements Command {
}
