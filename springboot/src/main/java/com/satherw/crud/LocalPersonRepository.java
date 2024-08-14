package com.satherw.crud;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
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

    public Iterable<Person> findPersons() {
        return this.persons;
    }

    public Optional<Person> findPersonById(Long id) {
        return this.persons.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    public void deleteById(Long id) {
        Optional<Person> personToDelete = this.findPersonById(id);

        if (personToDelete.isEmpty()) {
            return;
        }

        this.persons.remove(personToDelete.get());
    }
}
