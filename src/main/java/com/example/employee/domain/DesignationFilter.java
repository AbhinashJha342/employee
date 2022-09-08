package com.example.employee.domain;

import com.example.employee.exception.NotFoundException;
import com.example.employee.persistence.EmployeeRepository;
import com.example.employee.web.schema.DesignationType;

import java.util.List;

public class DesignationFilter implements Filter{

    private final String designation;

    public DesignationFilter(String designation) {
        this.designation = designation;
    }

    @Override
    public List<Employee> doFilter(EmployeeRepository employeeRepository) {
         return employeeRepository.findEmployeeByDesignation(DesignationType.valueOf(this.designation))
                 .orElseThrow(()-> new NotFoundException("there are no employees with role of "+this.designation));
    }
}
