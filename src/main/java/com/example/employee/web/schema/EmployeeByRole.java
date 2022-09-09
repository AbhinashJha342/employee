package com.example.employee.web.schema;

public class EmployeeByRole {

    private final String role;

    private final long count;

    public EmployeeByRole(String role, long count) {
        this.role = role;
        this.count = count;
    }

    public String getRole() {
        return role;
    }

    public long getCount() {
        return count;
    }
}
