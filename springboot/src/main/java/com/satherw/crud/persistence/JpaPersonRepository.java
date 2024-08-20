package com.satherw.crud.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPersonRepository extends CrudRepository<PersonEntity, Long> {}
