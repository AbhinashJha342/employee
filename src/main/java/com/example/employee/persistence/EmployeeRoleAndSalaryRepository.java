package com.example.employee.persistence;

import com.example.employee.domain.EmployeeRoleAndSalary;
import com.example.employee.web.schema.DesignationType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRoleAndSalaryRepository extends CrudRepository<EmployeeRoleAndSalary, Long> {

    EmployeeRoleAndSalary save(EmployeeRoleAndSalary employeeHistory);

    Optional<EmployeeRoleAndSalary> getAllByEmployeeIdAndEndDateIsNull(UUID employeeId);

    List<EmployeeRoleAndSalary> findAllByEndDateIsNull();

    List<EmployeeRoleAndSalary> getAllByRoleInAndEndDateIsNull(List<DesignationType> role);

}
