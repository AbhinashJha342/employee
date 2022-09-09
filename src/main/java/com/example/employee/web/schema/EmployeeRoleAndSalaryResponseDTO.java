package com.example.employee.web.schema;

import java.util.UUID;

public class EmployeeRoleAndSalaryResponseDTO {

    private final UUID employeeID;

    private final DesignationType role;

    private final String salary;

    private final String startDate;

    private final String endDate;

    public EmployeeRoleAndSalaryResponseDTO(UUID employeeID, DesignationType role, String salary, String startDate, String endDate) {
        this.employeeID = employeeID;
        this.role = role;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getEmployeeID() {
        return employeeID;
    }

    public DesignationType getRole() {
        return role;
    }

    public String getSalary() {
        return salary;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
