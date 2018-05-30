package com.example.reactivespring.web;

import com.example.reactivespring.model.Employee;
import com.example.reactivespring.model.Gender;
import com.example.reactivespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
  private final EmployeeService repository;

  @GetMapping("employees/{id}")
  public Mono<Employee> findById(@PathVariable Integer id) {
    return this.repository.findById(id);
  }

  @GetMapping(value = "/employees", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Employee> getAll() {
    return this.repository.findAll();
  }

  @GetMapping(value = "/employees/search/byGender/{gender}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Employee> findByGender(@PathVariable Gender gender) {
    return this.repository.findByGender(gender);
  }

}
