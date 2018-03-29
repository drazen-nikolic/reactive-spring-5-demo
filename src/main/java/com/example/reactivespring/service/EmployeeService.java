package com.example.reactivespring.service;

import com.example.reactivespring.model.Employee;
import com.example.reactivespring.model.Gender;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

  Mono<Employee> findById(Integer id);

  Flux<Employee> findAll();

  Flux<Employee> findByGender(Gender gender);
}
