package com.example.employee.service.impl;

import com.example.employee.domain.Change;
import com.example.employee.domain.EmployeeHistory;
import com.example.employee.persistence.EmployeeHistoryRepository;
import com.example.employee.service.HistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final EmployeeHistoryRepository employeeHistoryRepository;

    private final ObjectMapper objectMapper;

    private final TypeReference<HashMap<String, Object>> typeReference;

    public HistoryServiceImpl(EmployeeHistoryRepository employeeHistoryRepository, ObjectMapper objectMapper,TypeReference typeReference) {
        this.employeeHistoryRepository = employeeHistoryRepository;
        this.objectMapper = objectMapper;
        this.typeReference = typeReference;
    }

    @Override
    public void saveEmployeeHistory(UUID employeeID, Change old, Change update) {
        Map<String, Object> finalChangeMap = new HashMap<>();
        String oldData = null;
        String newData = null;
        try {
            oldData = Change.jsonTransformation(objectMapper, typeReference, old);
            newData = Change.jsonTransformation(objectMapper, typeReference, update);
        } catch (JsonProcessingException ex){

        }

        employeeHistoryRepository.save(new EmployeeHistory(oldData, newData, ZonedDateTime.now(), employeeID));
    }

    @Override
    public EmployeeHistory getEmployeeHistory(UUID employeeId) {
        return employeeHistoryRepository.getEmployeeByEmployeeID(employeeId);
    }
}
