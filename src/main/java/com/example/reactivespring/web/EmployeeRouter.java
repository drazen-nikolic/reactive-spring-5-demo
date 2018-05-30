package com.example.reactivespring.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class EmployeeRouter {
  @Bean
  public RouterFunction<ServerResponse> routeEmployees(EmployeeHandler employeeHandler) {
    return RouterFunctions
        .route(GET("/v2/employees").and(accept(MediaType.APPLICATION_STREAM_JSON)),
            employeeHandler::allEmployees);
  }

  @Bean
  public RouterFunction<ServerResponse> routeGetEmployee(EmployeeHandler employeeHandler) {
    return RouterFunctions
        .route(GET("/v2/employees/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)),
            employeeHandler::findEmployee);
  }

  @Bean
  public RouterFunction<ServerResponse> routeSearchByGenderEmployee(EmployeeHandler employeeHandler) {
    return RouterFunctions
        .route(GET("/v2/employees/search/byGender/{gender}").and(accept(MediaType.APPLICATION_STREAM_JSON)),
            employeeHandler::findEmployeeByGender);
  }

}
