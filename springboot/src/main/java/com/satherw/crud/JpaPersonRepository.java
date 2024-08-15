package com.satherw.crud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPersonRepository extends CrudRepository<PersonEntity, Long> {}
