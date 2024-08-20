package com.satherw.crud.persistence;

import com.satherw.crud.domain.PersonRepository;
import com.satherw.crud.domain.PersonRepositoryTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class DatabasePersonRepositoryTests extends PersonRepositoryTest {

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("crud")
            .withUsername("user")
            .withPassword("password");

    @BeforeAll
    static void beforeAll() {
        postgresDB.start();
    }

    @AfterAll
    static void afterAll() {
        postgresDB.stop();
    }

    @Autowired
    JpaPersonRepository jpaPersonRepository;

    @Override
    public PersonRepository getPersonRepository() {
        return new DatabasePersonRepository(jpaPersonRepository);
    }

}
