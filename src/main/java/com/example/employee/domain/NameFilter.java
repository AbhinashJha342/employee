package com.example.employee.domain;

import com.example.employee.exception.NotFoundException;
import com.example.employee.persistence.EmployeeRepository;

import java.util.List;

public class NameFilter implements Filter{

    private final String firstName;

    private final String lastName;

    public NameFilter(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public List<Employee> doFilter(EmployeeRepository employeeRepository) {
        return employeeRepository.findEmployeesByName_FirstContainingIgnoreCaseAndName_LastContainingIgnoreCaseAndDeletedIsFalse(this.firstName, this.lastName)
                .orElseThrow(()-> new NotFoundException("NO employee found whose first name contains "+this.firstName+" and last name contains "+this.lastName));
    }

}
