package com.example.springrest.payroll;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class EmployeeNotFoundAdvice {

  @ResponseBody //signals that this advice is rendered straight into the response body.
  @ExceptionHandler(EmployeeNotFoundException.class) //respond when exception thrown
  @ResponseStatus(HttpStatus.NOT_FOUND) //issue 404 as response
  String employeeNotFoundHandler(EmployeeNotFoundException ex) {
    return ex.getMessage();
  }
}