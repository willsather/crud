package com.satherw.crud;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPersons(Optional<String> filter) {
        List<Person> persons = this.personRepository.findPersons();

        if (filter.isEmpty()) {
            return persons;
        }

        return persons
                .stream()
                .filter(person -> person.getName().contains(filter.get()) || person.getSurname().contains(filter.get()))
                .collect(Collectors.toList());
    }

    public Optional<Person> getPerson(Long id) {
        return this.personRepository.findPerson(id);
    }

    public Person createPerson(Person person) {
        return personRepository.savePerson(person);
    }

    public void removePerson(Person person) {
        personRepository.deletePerson(person);
    }
}
