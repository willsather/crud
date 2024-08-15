package com.satherw.crud;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Person savePerson(Person person);

    Optional<Person> findPerson(Long id);

    List<Person> findPersons();

    void deletePerson(Person person);
}
