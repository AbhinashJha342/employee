package com.example.employee.domain;

import com.example.employee.persistence.EmployeeRepository;

import java.util.List;

public class DesignationFilter implements Filter{

    private final String designation;

    public DesignationFilter(String designation) {
        this.designation = designation;
    }

    @Override
    public List<Employee> doFilter(EmployeeRepository employeeRepository) {
         return null;
    }
}
