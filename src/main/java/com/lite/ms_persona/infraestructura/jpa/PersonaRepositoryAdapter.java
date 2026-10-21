package com.lite.ms_persona.infraestructura.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lite.ms_persona.domain.Persona;
import com.lite.ms_persona.domain.PersonaRepository;
import com.lite.ms_persona.infraestructura.mappers.PersonaMapper;

@Repository
public class PersonaRepositoryAdapter implements PersonaRepository {

    private final SpringDataPersonaRepository repository;

    public PersonaRepositoryAdapter(SpringDataPersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Persona save(Persona persona) {
        return PersonaMapper.toDomain(repository.save(PersonaMapper.toEntity(persona)));
    }

    @Override
    public Optional<Persona> findById(String id) {
        return repository.findById(id).map(PersonaMapper::toDomain);
    }

    @Override
    public List<Persona> findAll() {
        return repository.findAll().stream()
                .map(PersonaMapper::toDomain)
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
