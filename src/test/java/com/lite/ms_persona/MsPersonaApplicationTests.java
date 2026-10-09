package com.lite.ms_persona;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.lite.ms_persona.application.port.PersonaService;
import com.lite.ms_persona.domain.Persona;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class MsPersonaApplicationTests {

	@Autowired
	private PersonaService personaService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void clearDatabase() {
		personaService.readAllPersonas().forEach(persona -> personaService.deletePersona(persona.id()));
	}

	@Test
	void contextLoads() {
	}

	@Test
	void servesOpenApiDocumentation() throws Exception {
		mockMvc.perform(get("/v3/api-docs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.openapi").exists())
				.andExpect(jsonPath("$.paths['/v1/personas'].get").exists());
	}

	@Test
	void rejectsPersonaWithInvalidData() throws Exception {
		mockMvc.perform(post("/v1/personas")
				.contentType("application/json")
				.content("""
						{"nombre":" ","apellido":"Lovelace","email":"invalid","telefono":"555-0100"}
						"""))
				.andExpect(status().isBadRequest());
	}

	@Test
	void rejectsPersonaWithInvalidColombianPhone() throws Exception {
		mockMvc.perform(post("/v1/personas")
				.contentType("application/json")
				.content("""
						{"nombre":"Ada","apellido":"Lovelace","email":"ada@example.com","telefono":"123456"}
						"""))
				.andExpect(status().isBadRequest());
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
