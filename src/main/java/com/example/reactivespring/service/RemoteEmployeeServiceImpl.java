package com.example.reactivespring.service;

import com.example.reactivespring.model.Employee;
import com.example.reactivespring.model.Gender;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RemoteEmployeeServiceImpl implements EmployeeService {

  private final Flux<Employee> employees;

  @Override
  public Mono<Employee> findById(Integer id) {
    return employees.filter(e -> e.getId().equals(id)).next();
  }

  @Override
  public Flux<Employee> findAll() {
    return employees;
  }

  @Override
  public Flux<Employee> findByGender(Gender gender) {
    return employees.filter(e -> e.getGender().equals(gender));
  }
}
