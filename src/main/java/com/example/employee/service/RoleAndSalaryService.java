package com.example.employee.service;

import com.example.employee.domain.EmployeeRoleAndSalary;
import com.example.employee.web.schema.DesignationType;
import com.example.employee.web.schema.EmployeeRoleAndSalaryDTO;
import com.example.employee.web.schema.EmployeeRoleAndSalaryPatchDTO;
import com.example.employee.web.schema.EmployeeRoleDetails;

import java.util.List;
import java.util.UUID;

public interface RoleAndSalaryService {

    //void saveEmployeeHistory(UUID employeeID, Change old, Change update);

    void saveEmployeeRoleAndSalaryHistory(UUID employeeID, EmployeeRoleAndSalaryDTO employeeRoleAndSalaryDTO);

    EmployeeRoleAndSalary getEmployeeRoleAndSalaryHistory(UUID employeeId);

    EmployeeRoleAndSalaryPatchDTO updateRoleAndSalary(UUID employeeId, EmployeeRoleAndSalaryPatchDTO employeeRoleAndSalaryPatchDTO);

    List<EmployeeRoleDetails> getAllEmployeesByRole(List<DesignationType> role);

    List<EmployeeRoleDetails> findAllRoleAndSalary();

    void updateEndDate(UUID employeeID);
}
