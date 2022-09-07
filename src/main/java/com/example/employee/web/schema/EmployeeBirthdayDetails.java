package com.example.employee.web.schema;

import com.example.employee.domain.Employee;
import com.example.employee.domain.Name;

public class EmployeeBirthdayDetails {

    private final NameDTO name;

    private final String phone;

    private final String dateOfBirth;

    private final DesignationType designation;

    public EmployeeBirthdayDetails(Employee employee) {
        this.name = new NameDTO(employee.getName().getFirst(), employee.getName().getLast());
        this.phone = employee.getPhone();
        this.dateOfBirth = employee.getDateOfBirth();
        this.designation = employee.getDesignation();
    }

    public NameDTO getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public DesignationType getDesignation() {
        return designation;
    }
}
