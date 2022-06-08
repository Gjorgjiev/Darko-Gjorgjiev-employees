package com.springboot.test.employeesprojects.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

  private int employeeId;
  private int projectId;
  private LocalDate dateFrom;
  private LocalDate dateTo;

}
