package com.satherw.crud.domain;

import com.satherw.crud.persistence.LocalPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonServiceTests {

    private PersonService personService;

    @BeforeEach
    void setup() {
        this.personService = new PersonService(new LocalPersonRepository());
    }

    @Test
    public void canRetrieveAllPersons() {
        // no persons exist
        assertThat(personService.getPersons(Optional.empty())).hasSize(0);

        // create new person
        personService.createPerson(Person.builder().name("Will").surname("Sather").build());

        // one person exists
        assertThat(personService.getPersons(Optional.empty())).hasSize(1);

    }

    @Test
    public void canRetrieveFilteredPersons() {
        // no persons exist
        assertThat(personService.getPersons(Optional.empty())).hasSize(0);

        // create three new persons
        personService.createPerson(Person.builder().name("Will").surname("Sather").build());
        personService.createPerson(Person.builder().name("George").surname("Washington").build());
        personService.createPerson(Person.builder().name("John").surname("Adams").build());
        personService.createPerson(Person.builder().name("William").surname("Johnson").build());

        // three persons exists
        assertThat(personService.getPersons(Optional.empty())).hasSize(4);

        // only returns filtered people
        assertThat(personService.getPersons(Optional.of("Sather"))).hasSize(1);
        assertThat(personService.getPersons(Optional.of("George"))).hasSize(1);
        assertThat(personService.getPersons(Optional.of("Adams"))).hasSize(1);
        assertThat(personService.getPersons(Optional.of("Will"))).hasSize(2);
    }
}
