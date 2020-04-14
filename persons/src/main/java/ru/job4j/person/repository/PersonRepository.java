package ru.job4j.person.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.person.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}