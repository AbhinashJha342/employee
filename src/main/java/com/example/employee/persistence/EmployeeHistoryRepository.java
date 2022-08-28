package com.example.employee.persistence;

import com.example.employee.domain.EmployeeHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EmployeeHistoryRepository extends CrudRepository<EmployeeHistory, Long> {

    EmployeeHistory save(EmployeeHistory employeeHistory);

    EmployeeHistory getEmployeeByEmployeeID(UUID employeeID);
}
