package com.springboot.test.employeesprojects.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class PairOfEmployees {

  private long firstEmployeeId;
  private long secondEmployeeId;
  private long daysTogetherOnSameProject;
  private HashMap<Integer,Long> daysTogetherOnAllProjects;

  public PairOfEmployees(int firstEmployeeId, int secondEmployeeId) {
    this.firstEmployeeId = firstEmployeeId;
    this.secondEmployeeId = secondEmployeeId;
    this.daysTogetherOnSameProject = 0;
    this.daysTogetherOnAllProjects = new HashMap<>();
  }

  public void addProject(Integer projectId, Long days){
    this.daysTogetherOnAllProjects.put(projectId,days);
  }
}
