package com.example.reactivespring.web;

import com.example.reactivespring.model.Employee;
import com.example.reactivespring.model.Gender;
import com.example.reactivespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmployeeHandler {
  private final EmployeeService repository;

  public Mono<ServerResponse> allEmployees(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
        .body(repository.findAll(), Employee.class);
  }

  public Mono<ServerResponse> findEmployee(ServerRequest request) {
    Integer id = Integer.valueOf(request.pathVariable("id"));
    return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
        .body(repository.findById(id), Employee.class);
  }

  public Mono<ServerResponse> findEmployeeByGender(ServerRequest request) {
    Gender gender = Gender.valueOf(request.pathVariable("gender"));
    return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
        .body(repository.findByGender(gender), Employee.class);
  }
}
