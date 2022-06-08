package com.springboot.test.employeesprojects.services;

import com.springboot.test.employeesprojects.model.PairOfEmployees;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
  void readCsv();
  PairOfEmployees pairOfEmployeesThatWorkedTogether();

}
