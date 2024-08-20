package com.satherw.crud.persistence;

import com.satherw.crud.domain.PersonRepository;
import com.satherw.crud.domain.PersonRepositoryTest;

public class LocalPersonRepositoryTests extends PersonRepositoryTest {

    @Override
    public PersonRepository getPersonRepository() {
        return new LocalPersonRepository();
    }

}
