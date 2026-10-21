package com.lite.ms_persona.infraestructura.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPersonaRepository extends JpaRepository<PersonaEntity, String> {
}
