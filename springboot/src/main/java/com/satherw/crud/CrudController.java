package com.satherw.crud;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CrudController {

    private final PersonService personService;

    private final PersonRepository personRepository;

    public CrudController(PersonService personService, PersonRepository personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @GetMapping("/persons")
    public List<Person> getPersons(@RequestParam(value = "filter") Optional<String> filter) {
        return personService.getPersons(filter);
    }

    @GetMapping("/persons/{id}")
    ResponseEntity<Person> getPerson(@PathVariable Long id) {
        Optional<Person> person = this.personRepository.findPerson(id);

        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person savedPerson = personRepository.savePerson(person);

        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @DeleteMapping("/persons/{id}")
    ResponseEntity<Person> deletePerson(@PathVariable Long id) {
        Optional<Person> person = this.personRepository.findPerson(id);

        if (person.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.personRepository.deletePerson(person.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
