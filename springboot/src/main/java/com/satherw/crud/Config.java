package com.satherw.crud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    PersonRepository personRepository(JpaPersonRepository jpaPersonRepository) {
        return new DatabasePersonRepository(jpaPersonRepository);
    }

}