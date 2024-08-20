package com.satherw.crud.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class PersonRepositoryTest {

    private PersonRepository personRepository;

    public abstract PersonRepository getPersonRepository();

    @BeforeEach
    public void setup() {
        this.personRepository = getPersonRepository();
    }

    @Test
    public void canPerformPersonCrud() {
        // no persons exists
        assertThat(personRepository.findPersons()).hasSize(0);

        // add first person
        Person will = Person.builder().id(1L).name("Will").surname("Sather").build();
        personRepository.savePerson(will);

        assertThat(personRepository.findPersons()).contains(will);
        assertThat(personRepository.findPerson(1L)).isPresent().isEqualTo(Optional.of(will));

        // add another person
        Person george = Person.builder().id(2L).name("George").surname("Washington").build();
        personRepository.savePerson(george);
        assertThat(personRepository.findPersons()).contains(will, george);
        assertThat(personRepository.findPerson(2L)).isPresent().isEqualTo(Optional.of(george));

        // remove person 1
        personRepository.deletePerson(will);
        assertThat(personRepository.findPersons()).doesNotContain(will);
        assertThat(personRepository.findPersons()).contains(george);
        assertThat(personRepository.findPerson(1L)).isNotPresent();

        // only one person exists
        assertThat(personRepository.findPersons()).hasSize(1);

        // remove person 2
        personRepository.deletePerson(george);
        assertThat(personRepository.findPersons()).doesNotContain(george);
        assertThat(personRepository.findPerson(2L)).isNotPresent();

        // no persons exist
        assertThat(personRepository.findPersons()).hasSize(0);
    }
}
