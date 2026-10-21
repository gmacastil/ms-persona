package com.lite.ms_persona.infraestructura.mappers;

import com.lite.ms_persona.domain.Persona;
import com.lite.ms_persona.infraestructura.jpa.PersonaEntity;

public final class PersonaMapper {

    private PersonaMapper() {
    }

    public static PersonaEntity toEntity(Persona persona) {
        return new PersonaEntity(persona.id(), persona.nombre(), persona.apellido(), persona.email(),
                persona.telefono());
    }

    public static Persona toDomain(PersonaEntity entity) {
        return new Persona(entity.getNombre(), entity.getApellido(), entity.getEmail(), entity.getTelefono(),
                entity.getId());
    }
}
