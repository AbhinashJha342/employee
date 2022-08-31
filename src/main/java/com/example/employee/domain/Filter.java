package com.example.employee.domain;

import com.example.employee.persistence.EmployeeRepository;

import java.util.List;

public interface Filter {

    List<Employee> doFilter(EmployeeRepository employeeRepository);
}
