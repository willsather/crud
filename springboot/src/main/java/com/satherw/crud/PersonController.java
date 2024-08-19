package com.satherw.crud;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<Person> getPersons(@RequestParam(value = "filter") Optional<String> filter) {
        return personService.getPersons(filter);
    }

    @GetMapping("/persons/{id}")
    ResponseEntity<Person> getPerson(@PathVariable Long id) {
        Optional<Person> person = personService.getPerson(id);

        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);

        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @DeleteMapping("/persons/{id}")
    ResponseEntity<Person> deletePerson(@PathVariable Long id) {
        Optional<Person> person = personService.getPerson(id);

        if (person.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        personService.removePerson(person.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
