package com.lite.ms_persona.infraestructure.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPersonaRepository extends JpaRepository<PersonaEntity, String> {
}
