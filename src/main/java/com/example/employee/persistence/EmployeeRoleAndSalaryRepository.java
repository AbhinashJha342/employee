package com.example.employee.persistence;

import com.example.employee.domain.EmployeeRoleAndSalary;
import com.example.employee.domain.EmployeeSalaryAndDifferenceView;
import com.example.employee.web.schema.DesignationType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRoleAndSalaryRepository extends CrudRepository<EmployeeRoleAndSalary, Long> {

    EmployeeRoleAndSalary save(EmployeeRoleAndSalary employeeHistory);

    Optional<EmployeeRoleAndSalary> getAllByEmployeeIdAndEndDateIsNull(UUID employeeId);

    List<EmployeeRoleAndSalary> getAllByEmployeeId(UUID employeeId);

    List<EmployeeRoleAndSalary> findAllByEndDateIsNull();

    List<EmployeeRoleAndSalary> getAllByRoleInAndEndDateIsNull(List<DesignationType> role);

    @Query(nativeQuery = true, name = "salaryAndDiffMapping")
    EmployeeSalaryAndDifferenceView getEmployeeMinMaxSalaryAndDiff();

    @Query(value = "select * from employee_role_and_salary emprs where cast(salary as INTEGER) between :min and :max", nativeQuery = true)
    List<EmployeeRoleAndSalary> getEmployeesInSalaryRange(int min, int max);

}
