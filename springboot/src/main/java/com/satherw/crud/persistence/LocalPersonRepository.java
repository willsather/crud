package com.satherw.crud.persistence;

import com.satherw.crud.domain.Person;
import com.satherw.crud.domain.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalPersonRepository implements PersonRepository {
    private Long currentId = 1L;
    private final List<Person> persons;

    public LocalPersonRepository() {
        this.persons = new ArrayList<>();
    }

    public Person savePerson(Person person) {
        Person personToSave = Person.builder()
                .id(currentId++)
                .name(person.getName())
                .surname(person.getSurname())
                .build();

        this.persons.add(personToSave);

        return personToSave;
    }

    public Optional<Person> findPerson(Long id) {
        return this.persons.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    public List<Person> findPersons() {
        return this.persons;
    }

    public void deletePerson(Person person) {
        this.persons.remove(person);
    }
}
