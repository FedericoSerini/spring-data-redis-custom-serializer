package it.federicoserini.rediscustom.repository;

import it.federicoserini.rediscustom.data.Person;

import java.util.Map;

/**
 * @author Federico Serini
 */
public interface PersonRepository {
    void save(Person person);
    Person findById(Long id);
    Map<Long, Person> findAll();
}
