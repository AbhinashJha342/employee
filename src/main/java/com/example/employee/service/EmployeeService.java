package com.example.employee.service;

import com.example.employee.domain.Employee;
import com.example.employee.web.schema.State;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    void createEmployee(Employee employee);

    List<Employee> findByState(State state);

    List<Employee> findByDesignation(String designation);

    Employee updateEmployee(Employee employee);

    List<Employee> getEmployees(List<UUID> employeeIds);

    List<Employee> findAll();

    Employee getEmployee(UUID employeeId);
}
