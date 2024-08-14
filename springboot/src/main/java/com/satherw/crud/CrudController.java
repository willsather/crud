package com.satherw.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CrudController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return this.personRepository.findPersons();
    }

    @GetMapping("/persons/{id}")
    ResponseEntity<Person> getPerson(@PathVariable Long id) {
        Optional<Person> person = this.personRepository.findPerson(id);

        if (person.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(person.get(), HttpStatus.OK);
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
