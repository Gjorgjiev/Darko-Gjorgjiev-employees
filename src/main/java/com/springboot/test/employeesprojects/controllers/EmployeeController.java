package com.springboot.test.employeesprojects.controllers;

import com.springboot.test.employeesprojects.model.PairOfEmployees;
import com.springboot.test.employeesprojects.services.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

  @Autowired
  private EmployeeServiceImpl employeeService;

  @PostMapping("/upload")
  public ResponseEntity<PairOfEmployees> uploadCsv(){
    employeeService.readCsv();
    PairOfEmployees pairOfEmployees = employeeService.pairOfEmployeesThatWorkedTogether();
    return new ResponseEntity(pairOfEmployees, HttpStatus.OK);
  }

}
