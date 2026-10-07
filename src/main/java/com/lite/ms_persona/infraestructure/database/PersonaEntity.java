package com.lite.ms_persona.infraestructure.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.lite.ms_persona.domain.Persona;

@Entity
@Table(name = "personas")
public class PersonaEntity {

    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String email;

    private String telefono;

    protected PersonaEntity() {
    }

    private PersonaEntity(Persona persona) {
        this.id = persona.id();
        this.nombre = persona.nombre();
        this.apellido = persona.apellido();
        this.email = persona.email();
        this.telefono = persona.telefono();
    }

    public static PersonaEntity fromDomain(Persona persona) {
        return new PersonaEntity(persona);
    }

    public Persona toDomain() {
        return new Persona(nombre, apellido, email, telefono, id);
    }
}
