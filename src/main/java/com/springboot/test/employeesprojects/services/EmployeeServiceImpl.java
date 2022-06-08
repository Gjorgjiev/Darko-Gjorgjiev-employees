package com.springboot.test.employeesprojects.services;

import com.springboot.test.employeesprojects.model.Employee;
import com.springboot.test.employeesprojects.model.PairOfEmployees;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{

  private final List<Employee> employeesList = new ArrayList<>();

  private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(""
      + "[yyyy-MM-dd]" + "[dd-MM-yyyy]" + "[dd.MM.yyyy]" + "[yyyy.MM.dd]" + "[dd/MM/yyyy]"
      + "[yyyy/MM/dd]" + "[yyyy MM dd]" + "[dd MM yyyy]");

  @Override
  public void readCsv() {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("employees.csv"))) {
      String line;
      bufferedReader.readLine();
      while ((line = bufferedReader.readLine()) != null) {
        String replaceString = line.replaceAll("\\s", "");
        String[] split = replaceString.split(",");
        Employee employee = new Employee();
        int employeeId = Integer.parseInt(split[0]);
        int projectId = Integer.parseInt(split[1]);
        employee.setEmployeeId(employeeId);
        employee.setProjectId(projectId);
        employee.setDateFrom(LocalDate.parse(split[2], dateTimeFormatter));
        if (split[3].equalsIgnoreCase("NULL")) {
          employee.setDateTo(LocalDate.now());
        } else {
          employee.setDateTo(LocalDate.parse(split[3], dateTimeFormatter));
        }
        employeesList.add(employee);
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  @Override
  public PairOfEmployees pairOfEmployeesThatWorkedTogether() {
    HashMap<Integer, PairOfEmployees> map = new HashMap<>();
    long maxDaysOnSameProjects = 0;
    PairOfEmployees totalPairOfEmployees = null;
    for (int i = 0; i <= employeesList.size(); i++) {
      for (int j = i + 1; j < employeesList.size(); j++) {
        Employee firstEmployee = employeesList.get(i);
        Employee secondEmployee = employeesList.get(j);
        if (firstEmployee.getProjectId() == secondEmployee.getProjectId()) {
          hasEmployeesWorkedOnTheSameProjectAtTheSameTime(firstEmployee, secondEmployee);
          int employeeId = firstEmployee.getEmployeeId() + secondEmployee.getEmployeeId();
          long totalDaysOnSameProjects = durationOfEmployeesThatWorkedTogether(firstEmployee, secondEmployee);
          PairOfEmployees pairOfEmployees;
          if(map.containsKey(employeeId)) {
            pairOfEmployees = map.get(employeeId);
          } else {
            pairOfEmployees = new PairOfEmployees(firstEmployee.getEmployeeId(),secondEmployee.getEmployeeId());
          }
          pairOfEmployees.setDaysTogetherOnSameProject(totalDaysOnSameProjects);
          pairOfEmployees.addProject(firstEmployee.getProjectId(), totalDaysOnSameProjects * (-1));
          if (maxDaysOnSameProjects < pairOfEmployees.getDaysTogetherOnSameProject()) {
            maxDaysOnSameProjects = pairOfEmployees.getDaysTogetherOnSameProject();
            totalPairOfEmployees = pairOfEmployees;
          }
          map.put(employeeId, pairOfEmployees);
        }
      }
    }
    return totalPairOfEmployees;
  }

  private long durationOfEmployeesThatWorkedTogether(Employee firstEmployee, Employee secondEmployee) {
    LocalDate startDate = null;
    LocalDate endDate = null;
    if(firstEmployee.getDateFrom().isBefore(secondEmployee.getDateFrom())){
      startDate = secondEmployee.getDateFrom();
    }else {
      startDate = firstEmployee.getDateFrom();
    }

    if(firstEmployee.getDateTo().isBefore(secondEmployee.getDateTo())){
      endDate = firstEmployee.getDateTo();
    } else {
      endDate = secondEmployee.getDateTo();
    }

    return DAYS.between(startDate, endDate);
  }

  private boolean hasEmployeesWorkedOnTheSameProjectAtTheSameTime(Employee firstEmployee, Employee secondEmployee) {
    return (firstEmployee.getDateFrom().isBefore(secondEmployee.getDateTo()))
        && (firstEmployee.getDateTo().isAfter(secondEmployee.getDateFrom()));
  }
}
