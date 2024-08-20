package com.satherw.crud;

import com.satherw.crud.domain.PersonRepository;
import com.satherw.crud.domain.PersonService;
import com.satherw.crud.persistence.DatabasePersonRepository;
import com.satherw.crud.persistence.JpaPersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    PersonService personService(PersonRepository personRepository) {
        return new PersonService(personRepository);
    }

    @Bean
    PersonRepository personRepository(JpaPersonRepository jpaPersonRepository) {
        return new DatabasePersonRepository(jpaPersonRepository);
    }

}