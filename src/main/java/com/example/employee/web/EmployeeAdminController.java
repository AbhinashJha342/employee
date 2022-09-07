package com.example.employee.web;

import com.example.employee.domain.*;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.HistoryService;
import com.example.employee.web.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees/admin/employee")
public class EmployeeAdminController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HistoryService employeeHistory;

    @DeleteMapping(headers = "Employee-id")
    public ResponseEntity archieve(@RequestHeader("Employee-id") UUID employeeId){
        employeeService.archieveEmployee(employeeId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<EmployeeDetailsResponseDTO> createEmployee(@Valid @RequestBody EmployeeDetailsRequestDTO employeeDetailsRequestDTO){
        UUID employeeId = UUID.randomUUID();
        EmployeeDetailsResponseDTO result = employeeService.createEmployee(EmployeeDetailsRequestDTO.to(employeeId, employeeDetailsRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(headers = "Employee-ids")
    public ResponseEntity<List<EmployeeDetailsResponseDTO>> getEmployeeDetails(@RequestHeader(value = "Employee-ids", defaultValue = "")List<UUID> employeeIds){
            return new ResponseEntity(getEmployees(employeeIds)
                    .stream().map(Employee::from)
                    .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(headers = "Employee-id")
    public ResponseEntity<List<EmployeeDetailsResponseDTO>> getEmployeeDetails(@RequestHeader(value = "Employee-id", defaultValue = "")UUID employeeId){
        return new ResponseEntity(Employee.from(employeeService.getEmployee(employeeId)), HttpStatus.OK);
    }

    @GetMapping(params = {"state"})
    public ResponseEntity<List<EmployeeDetailsRequestDTO>> getEmployeesByState(@RequestParam(name = "state", defaultValue = "") String state ){
        return new ResponseEntity(employeeService.findByState(State.valueOf(state)).stream()
                .map(Employee::from)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PatchMapping(headers = "Employee-id")
    public ResponseEntity updateEmployeeDetails(@RequestHeader("Employee-id") UUID employeeId, @RequestBody EmployeeDetailsPatchRequestDTO employeeDetailspatchDTO){
        Employee employee = employeeService.updateEmployee(EmployeeDetailsPatchRequestDTO.to(employeeId, employeeDetailspatchDTO));
        return !ObjectUtils.isEmpty(employee) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT):
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(params = {"name.first", "name.last"})
    public ResponseEntity<List<EmployeeDetailsResponseDTO>> getEmployeeDetailsByName(@RequestParam(name = "name.first", required = false) String firstName,
                                                                               @RequestParam(name = "name.last", required = false) String lastName){

        if(ObjectUtils.isEmpty(firstName) && ObjectUtils.isEmpty(lastName))
            return new ResponseEntity((employeeService.findAll()
                    .stream().map(Employee::from)
                    .collect(Collectors.toList())), HttpStatus.OK);

        NameFilter filter = new NameFilter(firstName, lastName);
        List<Employee> employee = employeeService.findAllByFilter(filter);
        return !ObjectUtils.isEmpty(employee)?
                new ResponseEntity(employee.stream().map(Employee::from).collect(Collectors.toList()), HttpStatus.OK):
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(params = {"designation"})
    public ResponseEntity<List<EmployeeDetailsResponseDTO>> getEmployeeDetailsByDesignation(@RequestParam(name = "designation", required = false) String designation){

        if(ObjectUtils.isEmpty(designation))
            return new ResponseEntity((employeeService.findAll()
                    .stream().map(Employee::from)
                    .collect(Collectors.toList())), HttpStatus.OK);

        Filter filter = new DesignationFilter(designation);
        List<Employee> employee = employeeService.findAllByFilter(filter);
        return !ObjectUtils.isEmpty(employee)?
                new ResponseEntity(employee.stream().map(Employee::from).collect(Collectors.toList()), HttpStatus.OK):
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(params = {"gender"})
    public ResponseEntity<List<EmployeeGender>> getEmployeeCountByGender(@RequestParam(name = "gender", required = false) Set<String> gender){
        List<EmployeeGender> employeeGenderList = Arrays.asList();
        if(ObjectUtils.isEmpty(gender) || gender.size() == 2){
            List<Employee> employees = employeeService.findAll();
            EmployeeGender male = new EmployeeGender("Male", employees.stream().filter(employee -> employee.getGender().equalsIgnoreCase("Male")).count());
            EmployeeGender female = new EmployeeGender("Female", employees.stream().filter(employee -> employee.getGender().equalsIgnoreCase("Female")).count());
            employeeGenderList = Arrays.asList(male, female);
        } else {
            List<Employee> employees = employeeService.findByGender(gender.stream().findFirst().get());
            employeeGenderList = Arrays.asList(new EmployeeGender(gender.stream().findFirst().get(), employees.stream().count()));
        }

        return new ResponseEntity(employeeGenderList, HttpStatus.OK);
    }

    //@GetMapping(params = {"range"})
    public ResponseEntity<List<EmployeeGender>> getEmployeeSalaryCount(@RequestParam(name = "range", required = false) List<String> range){
        List<Employee> employees = employeeService.findAll();
        EmployeeGender male = new EmployeeGender("Male", employees.stream().filter(employee -> employee.getGender().equals("Male")).count());
        EmployeeGender female = new EmployeeGender("Female", employees.stream().filter(employee -> employee.getGender().equals("Female")).count());
        List<EmployeeGender> employeeGenderList = Arrays.asList(male, female);
        return new ResponseEntity(employeeGenderList, HttpStatus.OK);
    }

    @GetMapping(params = {"birthDate"})
    public ResponseEntity<List<Employee>> getEmployeeCountByGender(@RequestParam(name = "birthDate", required = false) String birthDate){
        return new ResponseEntity(employeeService.findByBirthdate(birthDate), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDetailsResponseDTO>> getEmployeeDetails(){
        return new ResponseEntity((employeeService.findAll()
                .stream().map(Employee::from)
                .collect(Collectors.toList())), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<EmployeeHistoryResponseDto> getEmployeeHistory(@RequestHeader("Employee-id") UUID employeeId){
       return new ResponseEntity(EmployeeHistory.from(employeeHistory.getEmployeeHistory(employeeId)), HttpStatus.OK);
    }

    private List<Employee> getEmployees(List<UUID> employeeIds){
        return !CollectionUtils.isEmpty(employeeIds) ? employeeService.getEmployees(employeeIds) : employeeService.findAll();
    }

}
