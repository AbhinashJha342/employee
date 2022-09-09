package com.example.employee.web.schema;

import com.example.employee.domain.EmployeeRoleAndSalary;

import java.util.UUID;

public class EmployeeRoleAndSalaryDTO {

    private final DesignationType role;

    private final String salary;

    private final String startingDate;


    public EmployeeRoleAndSalaryDTO(DesignationType role, String salary, String startingDate) {
        this.role = role;
        this.salary = salary;
        this.startingDate = startingDate;
    }

    public DesignationType getRole() {
        return role;
    }

    public String getSalary() {
        return salary;
    }

    public String getStartingDate() {
        return startingDate;
    }


    public static EmployeeRoleAndSalary to(EmployeeRoleAndSalaryDTO employeeRoleAndSalaryDTO, UUID employeeID){
        return new EmployeeRoleAndSalary(employeeRoleAndSalaryDTO.getRole(), employeeRoleAndSalaryDTO.getSalary(),
                employeeID, employeeRoleAndSalaryDTO.getStartingDate());
    }
}
