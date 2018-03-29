package com.example.reactivespring;

import static com.example.reactivespring.model.Gender.FEMALE;
import static com.example.reactivespring.model.Gender.MALE;

import com.example.reactivespring.model.Employee;
import com.example.reactivespring.service.EmployeeService;
import com.example.reactivespring.service.RemoteEmployeeServiceImpl;
import java.time.Duration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactivespringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactivespringApplication.class, args);
	}

	@Bean
	public EmployeeService employeeRepository() {
    var employees = Flux.just(
        new Employee(1, "Andrew", "Roberts", 31, MALE),
        new Employee(2, "Lisa", "Brooks", 25, FEMALE),
        new Employee(3, "Perry", "Jones", 45, MALE),
        new Employee(4, "Marc", "Webster", 50, MALE),
        new Employee(5, "Vivian", "Sidney", 42, FEMALE),
        new Employee(6, "Ramon", "Brown", 21, MALE),
        new Employee(7, "Beverly", "Williams", 36, FEMALE),
        new Employee(8, "Rosie", "Stevanson", 35, FEMALE)
    ).delayElements(Duration.ofSeconds(2));
		return new RemoteEmployeeServiceImpl(employees);
	}
}
