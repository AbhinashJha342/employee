package com.example.employee.web.schema;

public class EmployeeSalaryCount {

    private final long count;

    private final String salary;

    public EmployeeSalaryCount(long count, String salary) {
        this.count = count;
        this.salary = salary;
    }

    public long getCount() {
        return count;
    }

    public String getSalary() {
        return salary;
    }
}
