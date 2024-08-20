package com.satherw.crud.api;

import com.satherw.crud.domain.Person;
import com.satherw.crud.domain.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    List<Person> persons;

    @BeforeEach
    void setUp() {
        persons = List.of(
                new Person(1L, "Will", "Sather"),
                new Person(2L, "George", "Washington")
        );
    }

    @Test
    void shouldFindAllPersons() throws Exception {
        when(personService.getPersons(Optional.empty())).thenReturn(persons);

        mockMvc.perform(get("/persons"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("*", hasSize(2)));
    }

    @Test
    void shouldFindFilterPersons() throws Exception {
        when(personService.getPersons(Optional.of("Washing")))
                .thenReturn(List.of(new Person(2L, "George", "Washington")));

        mockMvc.perform(get("/persons?filter=Washing"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("*", hasSize(1)));
    }

    @Test
    void shouldDeletePerson() throws Exception {
        Person person = new Person(1L, "John", "Adams");

        when(personService.getPerson(1L)).thenReturn(Optional.of(person));
        doNothing().when(personService).removePerson(person);

        mockMvc.perform(delete("/persons/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(personService, times(1)).removePerson(person);
    }
}
