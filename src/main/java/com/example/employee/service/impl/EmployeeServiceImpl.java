package com.example.employee.service.impl;

import com.example.employee.domain.Change;
import com.example.employee.domain.Employee;
import com.example.employee.domain.EmployeeHistory;
import com.example.employee.exception.NotFoundException;
import com.example.employee.persistence.EmployeeHistoryRepository;
import com.example.employee.persistence.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import com.example.employee.web.schema.State;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final ObjectMapper objectMapper;

    private final TypeReference<HashMap<String, Object>> typeReference;

    //private final EmployeeHistoryRepository historyRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ObjectMapper objectMapper,TypeReference typeReference){
        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
        this.typeReference = typeReference;
        //this.historyRepository = historyRepository;
    }

    @Override
    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findByState(State state) {
        return employeeRepository.getEmployeeByAddress_State(state);
    }

    @Override
    public List<Employee> findByDesignation(String designation) {
        return employeeRepository.getEmployeeByDesignation(designation);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        String message = "";
        String finalChange = "";
        Employee existingEmployee = getEmployee(employee.getEmployeeId());
        if(ObjectUtils.isEmpty(existingEmployee)){
            throw new NotFoundException("Employee not found but trying to update it - employeeId: " + employee.getEmployeeId());
        }
        Employee updatedEmployee = employeeRepository.save(employee);
        Change existingData = new Change(existingEmployee.getDesignation(), existingEmployee.getSalary());
        Change updatedData = new Change(updatedEmployee.getDesignation(), updatedEmployee.getSalary());
        Map<String, Object> finalChangeMap = new HashMap<>();
        try {
            finalChangeMap.put("new", Change.jsonTransformation(objectMapper, typeReference, updatedData));
            finalChangeMap.put("old", Change.jsonTransformation(objectMapper, typeReference, existingData));
            finalChange = objectMapper.writeValueAsString(finalChangeMap);
        } catch (JsonProcessingException jsonProcessingException){
            message = jsonProcessingException.getMessage();
        }

        //addEmployeeHistory(finalChangeMap);

        return updatedEmployee;
    }

    @Override
    public List<Employee> getEmployees(List<UUID> employeeIds) {
        return employeeRepository.getEmployeeByEmployeeIdIn(employeeIds);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(UUID employeeId) {
        return employeeRepository.findEmployeesByEmployeeId(employeeId);
    }

}
