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
        PersonEntity entityToSave = PersonEntity.builder()
                .id(person.getId())
                .name(person.getName())
                .surname(person.getSurname())
                .build();

        PersonEntity savedEntity = jpaPersonRepository.save(entityToSave);

        return Person
                .builder()
                .id(savedEntity.getId())
                .name(savedEntity.getName())
                .surname(savedEntity.getSurname())
                .build();
    }

    public Optional<Person> findPerson(Long id) {
        Optional<PersonEntity> optionalEntity = jpaPersonRepository.findById(id);

        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }

        PersonEntity entity = optionalEntity.get();

        // TODO: is this how i'm supposed to return an Optional?
        return Optional.ofNullable(Person
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .build());
    }

    public List<Person> findPersons() {
        Iterable<PersonEntity> persons = this.jpaPersonRepository.findAll();

        // TODO: is this the best way to translate objects?
        return StreamSupport
                .stream(persons.spliterator(), false)
                .map(personEntity -> Person.builder()
                        .id(personEntity.getId())
                        .name(personEntity.getName())
                        .surname(personEntity.getSurname())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public void deletePerson(Person person) {
        this.jpaPersonRepository.deleteById(person.getId());
    }
}
