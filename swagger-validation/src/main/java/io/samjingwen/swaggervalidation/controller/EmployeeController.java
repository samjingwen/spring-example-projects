package io.samjingwen.swaggervalidation.controller;

import io.samjingwen.api.employees.Employee;
import io.samjingwen.api.employees.EmployeeApi;
import io.samjingwen.api.employees.IsSuccessful;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController implements EmployeeApi {

  @Override
  public CompletableFuture<ResponseEntity<Employee>> getEmployees(String employeeId) {
    return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
  }

  @Override
  public CompletableFuture<ResponseEntity<IsSuccessful>> postEmployees(Employee employee) {
    return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
  }

  @Override
  public CompletableFuture<ResponseEntity<IsSuccessful>> putEmployees(Employee employee) {
    return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
  }
}
