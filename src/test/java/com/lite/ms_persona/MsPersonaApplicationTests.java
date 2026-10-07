package com.lite.ms_persona;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.lite.ms_persona.application.port.PersonaService;
import com.lite.ms_persona.domain.Persona;

@SpringBootTest
@ActiveProfiles("test")
class MsPersonaApplicationTests {

	@Autowired
	private PersonaService personaService;

	@BeforeEach
	void clearDatabase() {
		personaService.readAllPersonas().forEach(persona -> personaService.deletePersona(persona.id()));
	}

	@Test
	void contextLoads() {
	}

	@Test
	void persistsPersonaThroughCrudUseCases() {
		Persona created = personaService.createPersona(
				new Persona("Ada", "Lovelace", "ada@example.com", "555-0100"));

		assertEquals(created, personaService.readPersona(created.id()));
		assertEquals(1, personaService.readAllPersonas().size());

		Persona updated = personaService.updatePersona(created.id(),
				new Persona("Ada", "Byron", "ada@example.com", "555-0101"));
		assertEquals("Byron", updated.apellido());

		personaService.deletePersona(created.id());
		assertThrows(NoSuchElementException.class, () -> personaService.readPersona(created.id()));
	}
}
