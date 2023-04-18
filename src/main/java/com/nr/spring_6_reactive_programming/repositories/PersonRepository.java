package com.nr.spring_6_reactive_programming.repositories;

import com.nr.spring_6_reactive_programming.domain.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {
    Mono<Person> getPersonById(int id);
    Flux<Person> findAll();
}
