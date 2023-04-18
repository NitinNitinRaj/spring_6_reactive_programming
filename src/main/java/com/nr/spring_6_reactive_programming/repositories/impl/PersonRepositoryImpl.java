package com.nr.spring_6_reactive_programming.repositories.impl;

import com.nr.spring_6_reactive_programming.domain.Person;
import com.nr.spring_6_reactive_programming.repositories.PersonRepository;
import java.util.List;
import java.util.Optional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

  Person nitin = Person
    .builder()
    .id(1)
    .firstName("Nitin")
    .lastName("Raj")
    .build();
  Person sarthak = Person
    .builder()
    .id(2)
    .firstName("Sarthak")
    .lastName("Gs")
    .build();
  Person narayan = Person
    .builder()
    .id(3)
    .firstName("Narayan")
    .lastName("Pai")
    .build();
  Person sushma = Person
    .builder()
    .id(4)
    .firstName("Sushma")
    .lastName("R")
    .build();

  @Override
  public Mono<Person> getPersonById(int id) {
    return findAll().filter(person -> person.getId() == id).next();
  }

  @Override
  public Flux<Person> findAll() {
    return Flux.just(nitin, sarthak, narayan, sushma);
  }
}
