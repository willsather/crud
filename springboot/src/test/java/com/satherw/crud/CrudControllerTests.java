package com.satherw.crud;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Transactional
@AutoConfigureMockMvc
class CrudControllerTests {

	@Container
	@ServiceConnection
	public static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("crud")
			.withUsername("user")
			.withPassword("password");

	@BeforeAll
	static void beforeAll() {
		postgresDB.start();
	}

	@AfterAll
	static void afterAll() {
		postgresDB.stop();
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Rollback
	void canCreateAPerson() throws Exception {
		// given there are no persons
		mockMvc.perform(get("/persons")).andExpect(jsonPath("*", hasSize(0)));

		// when a person is added
		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content("""
                {
                        "name": "Will",
                        "surname": "Sather"
                }
                """)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		// then there is one person
		mockMvc.perform(get("/persons")).andExpect(jsonPath("*", hasSize(1)));
	}

	@Test
	@Rollback
	void canCreateAPersonAndDeletePerson() throws Exception {
		// given there are no persons
		mockMvc.perform(get("/persons")).andExpect(jsonPath("*", hasSize(0)));

		// when a person is added
		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content("""
                {
                        "name": "Will",
                        "surname": "Sather"
                }
                """)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		// then there is one person
		mockMvc.perform(get("/persons")).andExpect(jsonPath("*", hasSize(1)));

		// when a person is deleted
		mockMvc.perform(delete("/persons/2")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		// then there is no person
		mockMvc.perform(get("/persons")).andExpect(jsonPath("*", hasSize(0)));
	}

	@Test
	@Rollback
	void canRetrieveASinglePerson() throws Exception {
		// given a person is stored
		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content("""
                {
                        "name": "Will",
                        "surname": "Sather"
                }
                """)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		// when a person is retrieved, then the data matches the person
		mockMvc.perform(get("/persons/3"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.id").value(3))
				.andExpect(jsonPath("$.name").value("Will"))
				.andExpect(jsonPath("$.surname").value("Sather"));
	}

	@Test
	public void canRetrieveZeroPersonsWithoutCreation() throws Exception {
		mockMvc.perform(get("/persons"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("*", hasSize(0)));
	}

	@Test
	public void cannotFindPerson() throws Exception {
		mockMvc.perform(get("/persons/10000"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
