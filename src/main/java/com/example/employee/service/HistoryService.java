package com.example.employee.service;

import com.example.employee.domain.Change;

public interface HistoryService {

    void saveEmployeeHistory(Change old, Change update);
}
