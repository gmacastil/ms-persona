package com.lite.ms_persona.domain.eventos;

import com.lite.ms_persona.domain.Persona;

public record PersonaCreada(Persona persona) implements PersonaEvento {
}
