package com.satherw.crud;

import java.util.Optional;

public interface PersonRepository {
    Person savePerson(Person person);

    Optional<Person> findPerson(Long id);

    Iterable<Person> findPersons();

    void deletePerson(Person person);
}
