package com.satherw.crud;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DatabasePersonRepository implements PersonRepository {

    private final JpaPersonRepository jpaPersonRepository;

    public DatabasePersonRepository(JpaPersonRepository jpaPersonRepository) {
        this.jpaPersonRepository = jpaPersonRepository;
    }

    public Person savePerson(Person person) {
        return toPerson(jpaPersonRepository.save(toEntity(person)));
    }

    public Optional<Person> findPerson(Long id) {
        return jpaPersonRepository.findById(id).map(this::toPerson);
    }

    public List<Person> findPersons() {
        Iterable<PersonEntity> persons = this.jpaPersonRepository.findAll();

        return StreamSupport
                .stream(persons.spliterator(), true)
                .map(this::toPerson)
                .collect(Collectors.toList());
    }

    public void deletePerson(Person person) {
        this.jpaPersonRepository.deleteById(person.getId());
    }

    private PersonEntity toEntity(Person person) {
        return PersonEntity.builder()
                .id(person.getId())
                .name(person.getName())
                .surname(person.getSurname())
                .build();
    }

    private Person toPerson(PersonEntity entity) {
        return Person.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .build();
    }
}
