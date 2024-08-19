package com.satherw.crud;

import org.springframework.data.repository.CrudRepository;

// TODO: Why doesn't this need @Repository (what is happening)
// TODO: look into Spring Data JDBC
public interface JpaPersonRepository extends CrudRepository<PersonEntity, Long> {}
