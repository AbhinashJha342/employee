package com.example.employee.service;

import com.example.employee.domain.Employee;
import com.example.employee.domain.Filter;
import com.example.employee.web.schema.EmployeeDetailsResponseDTO;
import com.example.employee.web.schema.State;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    EmployeeDetailsResponseDTO createEmployee(Employee employee);

    List<Employee> findByState(State state);

    Employee updateEmployee(Employee employee);

    List<Employee> getEmployees(List<UUID> employeeIds);

    List<Employee> findAll();

    Employee getEmployee(UUID employeeId);

    void archieveEmployee(UUID employeeId);

    List<Employee> findAllByFilter(Filter filter);

    List<Employee> findByBirthdate(String birthDate);

    List<Employee> findByGender(String gender);
}
