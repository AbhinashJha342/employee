package com.example.employee.web.schema;

import com.example.employee.domain.Email;
import com.example.employee.domain.Employee;
import com.example.employee.domain.EmployeeRoleAndSalary;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeRoleDetails {

    private final NameDTO name;

    private final DesignationType role;

    private final List<EmailDTO> emailDTO;

    private final String gender;

    public EmployeeRoleDetails(Employee employee, EmployeeRoleAndSalary employeeRoleAndSalary) {
        this.name = new NameDTO(employee.getName().getFirst(), employee.getName().getLast());
        this.role = employeeRoleAndSalary.getRole();
        this.emailDTO = employee.getEmail().stream().map(Email::from).collect(Collectors.toList());
        this.gender = employee.getGender();
    }

    public NameDTO getName() {
        return name;
    }

    public DesignationType getRole() {
        return role;
    }

    public List<EmailDTO> getEmailDTO() {
        return emailDTO;
    }

    public String getGender() {
        return gender;
    }
}
