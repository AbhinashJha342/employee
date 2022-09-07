package com.example.employee.web.schema;

public class EmployeeGender {

    private final String gender;

    private final long count;

    public EmployeeGender(String gender, long count) {
        this.gender = gender;
        this.count = count;
    }

    public String getGender() {
        return gender;
    }

    public long getCount() {
        return count;
    }
}
