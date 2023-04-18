package com.nr.spring_6_reactive_programming.repositories.impl;

import com.nr.spring_6_reactive_programming.domain.Person;
import com.nr.spring_6_reactive_programming.repositories.PersonRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImplTest {

  PersonRepository personRepository = new PersonRepositoryImpl();

  @Test
  void testGetByIdBlocking() {
    Mono<Person> personMono = personRepository.getPersonById(1);
    Person person = personMono.block();
    System.out.println(person);
  }

  @Test
  void testGetByIdSubscriber() {
    Mono<Person> personMono = personRepository.getPersonById(1);
    personMono.subscribe(person -> {
      System.out.println(person);
    });
  }

  @Test
  void testGetByIdMap() {
    Mono<Person> personMono = personRepository.getPersonById(1);
    personMono.map(Person::getFirstName).subscribe(System.out::println);
  }

  @Test
  void testFluxFindAllBlockFirst() {
    Flux<Person> personFlux = personRepository.findAll();
    Person person = personFlux.blockFirst();
    System.out.println(person);
  }

  @Test
  void testFluxFindAllSubsriber() {
    Flux<Person> personFlux = personRepository.findAll();
    personFlux.subscribe(person -> System.out.println(person));
  }

  @Test
  void testFluxFindAllMap() {
    Flux<Person> personFlux = personRepository.findAll();
    personFlux.map(Person::getFirstName).subscribe(System.out::println);
  }

  @Test
  void testFluxFindAllList() {
    Flux<Person> personFlux = personRepository.findAll();
    Mono<List<Person>> list = personFlux.collectList();
    list.subscribe(li -> {
      li.forEach(System.out::println);
    });
  }

  @Test
  void testFilter() {
    personRepository
      .findAll()
      .filter(person -> person.getFirstName().equals("Nitin"))
      .subscribe(System.out::println);
  }

  @Test
  void testFilterReturingMono() {
    Mono<Person> nitin = personRepository
      .findAll()
      .filter(person -> person.getFirstName().equals("Nitin"))
      .next();

    nitin.subscribe(System.out::println);
  }

  @Test
  void testErrorHandling() {
    final int id = 8;
    Mono<Person> single = personRepository
      .findAll()
      .filter(person -> person.getId() == id)
      .single()
      .doOnError(throwable -> {
        System.out.println("Error on flux");

        System.out.println(throwable.toString());
      });

    single.subscribe(
      System.out::println,
      throwable -> {
        System.out.println("Error on mono");

        System.out.println(throwable.toString());
      }
    );
  }

  @Test
  void testGetById() {
    Mono<Person> personMono = personRepository.getPersonById(2);
    assertTrue(personMono.hasElement().block());
  }

  @Test
  void testGetByIdNoElement() {
    Mono<Person> personMono = personRepository.getPersonById(9);
    assertFalse(personMono.hasElement().block());
  }
}