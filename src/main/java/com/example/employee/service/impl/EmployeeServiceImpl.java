package com.example.employee.service.impl;

import com.example.employee.domain.Employee;
import com.example.employee.domain.Filter;
import com.example.employee.exception.NotFoundException;
import com.example.employee.persistence.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.RoleAndSalaryService;
import com.example.employee.web.schema.EmployeeDetailsResponseDTO;
import com.example.employee.web.schema.State;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final RoleAndSalaryService roleAndSalaryService;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleAndSalaryService historyService){
        this.employeeRepository = employeeRepository;
        this.roleAndSalaryService = historyService;
    }

    @Override
    public EmployeeDetailsResponseDTO createEmployee(Employee employee) {
       return Employee.from(employeeRepository.save(employee));
    }

    @Override
    public List<Employee> findByState(State state) {
        return employeeRepository.getEmployeeByAddress_StateAndDeletedFalse(state)
                .orElseThrow(()-> new NotFoundException("no employees found who are from state "+state.toString()));
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Employee existingEmployee = getEmployee(employee.getEmployeeId());
        if(ObjectUtils.isEmpty(existingEmployee)) {
            throw new NotFoundException("Employee not found but trying to update it - employeeId: " + employee.getEmployeeId());
        }
        //Change existingData = new Change(existingEmployee.getDesignation().toString(), existingEmployee.getSalary());
        existingEmployee.setEmployeeId(employee.getEmployeeId());

        if(existingEmployee.equals(employee)){
            return employee;
        }

        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setDateOfBirth(employee.getDateOfBirth());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setName(employee.getName());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.getEmail().clear();
        existingEmployee.getEmail().addAll(employee.getEmail());

        existingEmployee.getAddress().setEmployee(existingEmployee);

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return updatedEmployee;
    }

    @Override
    public List<Employee> getEmployees(List<UUID> employeeIds) {
        return employeeRepository.getEmployeeByEmployeeIdInAndDeletedIsFalse(employeeIds).
                orElseThrow(()-> new NotFoundException("no employees found with the passed uuids "+employeeIds.stream().toString()));
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> result = employeeRepository.findAllByDeletedIsFalse();
        if(result.size() == 0)
            throw new NotFoundException("No employee found.");

        return result;
    }

    @Override
    public Employee getEmployee(UUID employeeId) {
        return employeeRepository.findEmployeesByEmployeeIdAndDeletedIsFalse(employeeId)
                .orElseThrow(() -> new NotFoundException("No employee found with the employeeid "+employeeId));
    }

    @Override
    public void archieveEmployee(UUID employeeId) {
        Employee employee = getEmployee(employeeId);
        employee.setDeleted(true);
        employeeRepository.save(employee);
        roleAndSalaryService.updateEndDate(employeeId);
    }

    @Override
    public List<Employee> findAllByFilter(Filter filter) {
        return filter.doFilter(employeeRepository);
    }

    @Override
    public List<Employee> findByBirthdate(String birthDate) {
        ZonedDateTime dateOfBirth = null;

        if(!ObjectUtils.isEmpty(birthDate)){
            LocalDate date = LocalDate.parse(birthDate);
            dateOfBirth = date.atStartOfDay(ZoneId.systemDefault());
        } else {
            dateOfBirth = ZonedDateTime.now();
        }

        Optional<List<Employee>> employees = employeeRepository.findEmployeeByDateOfBirthBetween(dateOfBirth);
        employees.orElseThrow(() -> new NotFoundException("no employee found whose birthday is on date:"+birthDate+"or in the week ahead."));
        return employees.get();
    }

    @Override
    public List<Employee> findByGender(String gender) {
        Optional<List<Employee>> employees = employeeRepository.findEmployeeByGenderAndDeletedFalse(gender);
        employees.orElseThrow(()-> new NotFoundException("no employees found who are "+gender));
        return employees.get();
    }
}
