package com.satherw.crud;

public interface PersonRepository {
    Person savePerson(Person person);

    Iterable<Person> findPersons();

    void deleteById(Long id);
}
