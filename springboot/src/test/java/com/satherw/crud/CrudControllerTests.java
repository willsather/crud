package com.satherw.crud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class CrudControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
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
		mockMvc.perform(delete("/persons/1")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		// then there is no person
		mockMvc.perform(get("/persons")).andExpect(jsonPath("*", hasSize(0)));
	}

	@Test
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
		mockMvc.perform(get("/persons/2"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.name").value("Will"))
				.andExpect(jsonPath("$.surname").value("Sather"));
	}

}
