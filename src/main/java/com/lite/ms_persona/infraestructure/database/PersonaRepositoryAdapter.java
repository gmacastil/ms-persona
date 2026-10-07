package com.lite.ms_persona.infraestructure.database;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lite.ms_persona.domain.Persona;
import com.lite.ms_persona.domain.port.PersonaRepository;

@Repository
public class PersonaRepositoryAdapter implements PersonaRepository {

    private final SpringDataPersonaRepository repository;

    public PersonaRepositoryAdapter(SpringDataPersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Persona save(Persona persona) {
        return repository.save(PersonaEntity.fromDomain(persona)).toDomain();
    }

    @Override
    public Optional<Persona> findById(String id) {
        return repository.findById(id).map(PersonaEntity::toDomain);
    }

    @Override
    public List<Persona> findAll() {
        return repository.findAll().stream()
                .map(PersonaEntity::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
