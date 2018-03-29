package com.example.reactivespring.service;

import static com.example.reactivespring.model.Gender.FEMALE;
import static com.example.reactivespring.model.Gender.MALE;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.example.reactivespring.model.Employee;
import org.junit.Before;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

  private class EmployeeSubscriber implements Subscriber<Employee> {

    private int count = 0;
    private Subscription subscription;

    public int getCount() {
      return count;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
      this.subscription = subscription;
      subscription.request(1);
    }

    @Override
    public void onNext(Employee item) {
      count++;
      subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {}

    @Override
    public void onComplete() {
    }
  }

  @Test
  public void testFindById() {
    Mono<Employee> employeeMono = service.findById(1);
    employeeMono.subscribe(e -> assertThat(e.getFirstName(), is("Andrew")));
  }

  @Test
  public void testFindByIdNotFound() {
    Mono<Employee> employeeMono = service.findById(-999);
    employeeMono.subscribe(e -> assertThat(e, is(nullValue())));
  }

  @Test
  public void testFindAll() {
    var employeeSubscriber = new EmployeeSubscriber();

    service.findAll().subscribe(employeeSubscriber);

    assertThat(employeeSubscriber.getCount(), is(3));
  }

  @Test
  public void testFindByGender() {
    var employeeSubscriber = new EmployeeSubscriber();

    service.findByGender(MALE).subscribe(employeeSubscriber);

    assertThat(employeeSubscriber.getCount(), is(2));
  }
}