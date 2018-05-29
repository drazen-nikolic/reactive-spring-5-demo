package com.example.reactivespring.service;

import static com.example.reactivespring.model.Gender.FEMALE;
import static com.example.reactivespring.model.Gender.MALE;

import com.example.reactivespring.model.Employee;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class RemoteEmployeeServiceImplTest {

  private EmployeeService service;

  @Before
  public void setup() {
    service = new RemoteEmployeeServiceImpl(Flux.just(
        new Employee(1, "Andrew", "Roberts", 31, MALE),
        new Employee(2, "Lisa", "Brooks", 25, FEMALE),
        new Employee(3, "Perry", "Jones", 45, MALE)
    )
    );
  }

  @Test
  public void testFindById() {
    Mono<Employee> employeeMono = service.findById(1);
    StepVerifier.create(employeeMono)
        .assertNext(e -> e.getFirstName().equals("Andrew"))
        .expectComplete()
        .verify();
  }

  @Test
  public void testFindByIdNotFound() {
    Mono<Employee> employeeMono = service.findById(-999);
    StepVerifier.create(employeeMono)
        .expectComplete()
        .verify();
  }

  @Test
  public void testFindAll() {
    Flux<Employee> employeeFlux = service.findAll();

    StepVerifier.create(employeeFlux.map(Employee::getFirstName))
        .expectNext("Andrew", "Lisa", "Perry")
        .expectComplete()
        .verify();
  }

  @Test
  public void testFindByGender() {
    Flux<Employee> employeeFlux = service.findByGender(MALE);

    StepVerifier.create(employeeFlux.map(Employee::getFirstName))
        .expectNext("Andrew", "Perry")
        .expectComplete()
        .verify();
  }
}