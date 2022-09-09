package com.example.employee.web.schema;

import java.util.Optional;

public class EmployeeRoleAndSalaryPatchDTO {

    private final DesignationType role;

    private final Optional<String> salary;

    private final Optional<String> startingDate;

    private final Optional<String> endDate;


    public EmployeeRoleAndSalaryPatchDTO(DesignationType role, Optional<String> salary, Optional<String> startingDate, Optional<String> endDate) {
        this.role = role;
        this.salary = salary;
        this.startingDate = startingDate;
        this.endDate = endDate;
    }

    public boolean canBeUpdated(String endDate, DesignationType designationType, String startDate) {
        return designationType.getValue().equalsIgnoreCase(this.role.getValue()) && endDate == null && startDate.equals(this.getStartingDate().get());
    }

    public DesignationType getRole() {
        return role;
    }

    public Optional<String> getSalary() {
        return salary;
    }

    public Optional<String> getStartingDate() {
        return startingDate;
    }

    public Optional<String> getEndDate() {
        return endDate;
    }
}
