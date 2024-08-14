package com.satherw.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CrudController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return this.personRepository.findPersons();
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person savedPerson = personRepository.savePerson(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @DeleteMapping("/persons/{id}")
    void deleteEmployee(@PathVariable Long id) {
        this.personRepository.deleteById(id);
    }
}
