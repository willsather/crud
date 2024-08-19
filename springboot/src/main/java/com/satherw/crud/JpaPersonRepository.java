package com.satherw.crud;

import org.springframework.data.repository.CrudRepository;

public interface JpaPersonRepository extends CrudRepository<PersonEntity, Long> {}
