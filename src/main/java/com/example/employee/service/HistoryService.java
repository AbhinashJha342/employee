package com.example.employee.service;

import com.example.employee.domain.Change;
import com.example.employee.domain.EmployeeHistory;

import java.util.UUID;

public interface HistoryService {

    void saveEmployeeHistory(UUID employeeID, Change old, Change update);

    EmployeeHistory getEmployeeHistory(UUID employeeId);
}
