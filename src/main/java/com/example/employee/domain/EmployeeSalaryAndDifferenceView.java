package com.example.employee.domain;

import javax.persistence.*;

@SqlResultSetMapping(
        name="salaryAndDiffMapping",
        classes={
                @ConstructorResult(
                        targetClass=com.example.employee.domain.EmployeeSalaryAndDifferenceView.class,
                        columns={
                                @ColumnResult(name="salaryDiff"),
                                @ColumnResult(name="minSalary"),
                                @ColumnResult(name="maxSalary")
                        }
                )
        }
)
@NamedNativeQuery(name = "salaryAndDiffMapping", query = "SELECT max(cast(salary as INTEGER)) as maxSalary, min(cast(salary as INTEGER)) as minSalary , max(cast(salary as INTEGER)) - min(cast(salary as INTEGER)) as salaryDiff\n" +
        "from employee_role_and_salary", resultClass = EmployeeSalaryAndDifferenceView.class, resultSetMapping = "salaryAndDiffMapping")
@Entity
public class EmployeeSalaryAndDifferenceView {

    private int salaryDiff;

    private int minSalary;

    private int maxSalary;
    private Long id;

    public EmployeeSalaryAndDifferenceView(int salaryDiff, int minSalary, int maxSalary) {
        this.salaryDiff = salaryDiff;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public EmployeeSalaryAndDifferenceView() {
    }

    public int getSalaryDiff() {
        return salaryDiff;
    }

    public void setSalaryDiff(int salaryDiff) {
        this.salaryDiff = salaryDiff;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
