package com.example.employee.domain;

import com.example.employee.web.schema.DesignationType;
import com.example.employee.web.schema.EmployeeRoleAndSalaryResponseDTO;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class EmployeeRoleAndSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DesignationType role;

    private String salary;

    private UUID employeeId;

    @Column(nullable = false)
    private String startDate;

    private String endDate;


    public EmployeeRoleAndSalary(DesignationType role, String salary, UUID employeeId, String startDate, String endDate) {
        this.role = role;
        this.salary = salary;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EmployeeRoleAndSalary(DesignationType role, String salary, UUID employeeId, String startDate) {
        this.role = role;
        this.salary = salary;
        this.employeeId = employeeId;
        this.startDate = startDate;
    }

    public EmployeeRoleAndSalary() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DesignationType getRole() {
        return role;
    }

    public void setRole(DesignationType role) {
        this.role = role;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public static EmployeeRoleAndSalaryResponseDTO to(EmployeeRoleAndSalary roleAndSalaryHistory) {
        return new EmployeeRoleAndSalaryResponseDTO(roleAndSalaryHistory.getEmployeeId(), roleAndSalaryHistory.getRole() , roleAndSalaryHistory.getSalary(), roleAndSalaryHistory.getStartDate(), roleAndSalaryHistory.getEndDate());
    }
}
