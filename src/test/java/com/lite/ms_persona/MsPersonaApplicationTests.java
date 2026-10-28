package com.lite.ms_persona;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.lite.ms_persona.application.bus.CommandBus;
import com.lite.ms_persona.application.command.ActualizarPersonaCommand;
import com.lite.ms_persona.application.command.CrearPersonaCommand;
import com.lite.ms_persona.application.command.EliminarPersonaCommand;
import com.lite.ms_persona.application.query.PersonaQueryService;
import com.lite.ms_persona.domain.Persona;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
class MsPersonaApplicationTests {

	@Container
	@ServiceConnection
	static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4");

	@Container
	@ServiceConnection(name = "redis")
	static final GenericContainer<?> valkey = new GenericContainer<>("valkey/valkey:8.1-alpine")
			.withExposedPorts(6379)
			.waitingFor(Wait.forLogMessage(".*Ready to accept connections.*", 1));

	@Autowired
	private CommandBus commandBus;

	@Autowired
	private PersonaQueryService personaQueryService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CacheManager cacheManager;

	@BeforeEach
	void clearDatabase() {
		personaQueryService.todas()
				.forEach(persona -> commandBus.dispatch(new EliminarPersonaCommand(persona.id())));
	}

	@Test
	void contextLoads() {
	}

	@Test
	void servesOpenApiDocumentation() throws Exception {
		mockMvc.perform(get("/v3/api-docs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.openapi").exists())
				.andExpect(jsonPath("$.paths['/v2/personas'].get").exists());
	}

	@Test
	void rejectsPersonaWithInvalidData() throws Exception {
		mockMvc.perform(post("/v2/personas")
				.contentType("application/json")
				.content("""
						{"nombre":" ","apellido":"Lovelace","email":"invalid","telefono":"555-0100"}
						"""))
				.andExpect(status().isBadRequest());
	}

	@Test
	void rejectsPersonaWithInvalidColombianPhone() throws Exception {
		mockMvc.perform(post("/v2/personas")
				.contentType("application/json")
				.content("""
						{"nombre":"Ada","apellido":"Lovelace","email":"ada@example.com","telefono":"123456"}
						"""))
				.andExpect(status().isBadRequest());
	}

	@Test
	void persistsPersonaThroughCommandsAndQueries() {
		Persona created = commandBus.dispatch(
				new CrearPersonaCommand(new Persona("Ada", "Lovelace", "ada@example.com", "3001234567")));

		assertEquals(created, personaQueryService.porId(created.id()));
		assertEquals(1, personaQueryService.todas().size());

		Persona updated = commandBus.dispatch(
				new ActualizarPersonaCommand(created.id(),
						new Persona("Ada", "Byron", "ada@example.com", "3001234568")));
		assertEquals("Byron", updated.apellido());

		commandBus.dispatch(new EliminarPersonaCommand(created.id()));
		assertThrows(NoSuchElementException.class, () -> personaQueryService.porId(created.id()));
	}

	@Test
	void cachesPersonaByIdAndEvictsOnUpdateAndDelete() {
		Persona created = commandBus.dispatch(
				new CrearPersonaCommand(new Persona("Ada", "Lovelace", "ada@example.com", "3001234567")));
		Cache cache = cacheManager.getCache(PersonaQueryService.CACHE_NAME);

		personaQueryService.porId(created.id());
		await().atMost(Duration.ofSeconds(5))
				.untilAsserted(() -> assertNotNull(cache.get(created.id())));

		commandBus.dispatch(new ActualizarPersonaCommand(created.id(),
				new Persona("Ada", "Byron", "ada@example.com", "3001234568")));
		await().atMost(Duration.ofSeconds(5))
				.untilAsserted(() -> assertNull(cache.get(created.id())));

		personaQueryService.porId(created.id());
		await().atMost(Duration.ofSeconds(5))
				.untilAsserted(() -> assertNotNull(cache.get(created.id())));

		commandBus.dispatch(new EliminarPersonaCommand(created.id()));
		await().atMost(Duration.ofSeconds(5))
				.untilAsserted(() -> assertNull(cache.get(created.id())));
	}
}
