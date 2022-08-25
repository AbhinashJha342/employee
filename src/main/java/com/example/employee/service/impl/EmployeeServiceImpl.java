package com.example.employee.service.impl;

import com.example.employee.domain.Change;
import com.example.employee.domain.Employee;
import com.example.employee.domain.EmployeeHistory;
import com.example.employee.exception.NotFoundException;
import com.example.employee.persistence.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.HistoryService;
import com.example.employee.web.schema.EmployeeDetailsResponseDTO;
import com.example.employee.web.schema.State;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final HistoryService historyService;

    //private final EmployeeHistoryRepository historyRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, HistoryService historyService){
        this.employeeRepository = employeeRepository;
        this.historyService = historyService;
    }

    @Override
    public EmployeeDetailsResponseDTO createEmployee(Employee employee) {
       return Employee.from(employeeRepository.save(employee));
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
        Employee existingEmployee = getEmployee(employee.getEmployeeId());
        if(ObjectUtils.isEmpty(existingEmployee)) {
            throw new NotFoundException("Employee not found but trying to update it - employeeId: " + employee.getEmployeeId());
        }
        Change existingData = new Change(existingEmployee.getDesignation(), existingEmployee.getSalary());
        existingEmployee.setEmployeeId(employee.getEmployeeId());

        if(existingEmployee.equals(employee)){
            return employee;
        }

        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setDateOfBirth(employee.getDateOfBirth());
        existingEmployee.setDesignation(employee.getDesignation());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setName(employee.getName());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setSalary(employee.getSalary());
        //existingEmployee.setEmail(employee.getEmail());
        existingEmployee.getEmail().clear();
        existingEmployee.getEmail().addAll(employee.getEmail());

        //existingEmployee.getEmail().forEach(email -> email.setEmployee(existingEmployee));
        existingEmployee.getAddress().setEmployee(existingEmployee);

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        Change updatedData = new Change(updatedEmployee.getDesignation(), updatedEmployee.getSalary());
        historyService.saveEmployeeHistory(existingData, updatedData);

        return updatedEmployee;
    }

    @Override
    public List<Employee> getEmployees(List<UUID> employeeIds) {
        return employeeRepository.getEmployeeByEmployeeIdIn(employeeIds);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Employee getEmployee(UUID employeeId) {
        return employeeRepository.findEmployeesByEmployeeIdAndDeletedIsFalse(employeeId);
    }

    @Override
    public void archieveEmployee(UUID employeeId) {
        Employee employee = getEmployee(employeeId);
        employee.setDeleted(true);
        employeeRepository.save(employee);
    }

}
