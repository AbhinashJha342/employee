package com.example.employee.web;

import com.example.employee.domain.Employee;
import com.example.employee.domain.EmployeeRoleAndSalary;
import com.example.employee.domain.NameFilter;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.RoleAndSalaryService;
import com.example.employee.web.schema.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/employees")
public class EmployeeAdminController {

    private final EmployeeService employeeService;

    private final RoleAndSalaryService roleAndSalaryService;

    public EmployeeAdminController(EmployeeService employeeService, RoleAndSalaryService roleAndSalaryService) {
        this.employeeService = employeeService;
        this.roleAndSalaryService = roleAndSalaryService;
    }

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
    public ResponseEntity<EmployeeDetailsResponseDTO> getEmployeeDetails(@RequestHeader(value = "Employee-id", defaultValue = "")UUID employeeId){
        return new ResponseEntity(Employee.from(employeeService.getEmployee(employeeId)), HttpStatus.OK);
    }

    @GetMapping(params = {"state"})
    public ResponseEntity<List<EmployeeByState>> getEmployeesByState(@RequestParam(name = "state", defaultValue = "") String state ){
        return new ResponseEntity(employeeService.findByState(State.valueOf(state)).stream()
                .map(employee -> new EmployeeByState(employee))
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

    @GetMapping(value = {"/role", "/role/{role}"} )
    public ResponseEntity<List<EmployeeRoleDetails>> getEmployeeDetailsByDesignation(@PathVariable(required = false) String role){

        if(ObjectUtils.isEmpty(role))
            return new ResponseEntity(roleAndSalaryService.findAllRoleAndSalary(), HttpStatus.OK);

        return new ResponseEntity(roleAndSalaryService.getAllEmployeesByRole(Arrays.asList(DesignationType.valueOf(role))), HttpStatus.OK);
    }

    @GetMapping(params = {"gender"})
    public ResponseEntity<List<EmployeeGender>> getEmployeeCountByGender(@RequestParam(name = "gender", required = false) Set<String> gender){
        List<EmployeeGender> employeeGenderList;
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

    @GetMapping(params = {"range"})
    public ResponseEntity<List<EmployeeGender>> getEmployeeSalaryCount(@RequestParam(name = "range", required = false, defaultValue = "5") String range){
        return new ResponseEntity(roleAndSalaryService.getSalaryDiff(Integer.parseInt(range)), HttpStatus.OK);
    }

    @GetMapping(params = {"birthDate"})
    public ResponseEntity<List<EmployeeBirthdayDetails>> getEmployeeCountByGender(@RequestParam(name = "birthDate", required = false) String birthDate){
        return new ResponseEntity(
                employeeService.findByBirthdate(birthDate)
                        .stream()
                        .map(employee -> new EmployeeBirthdayDetails(employee))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(params = {"roles"})
    public ResponseEntity<List<EmployeeByRole>> getEmployeeByRole(@RequestParam(name = "roles") List<DesignationType> roles){
        List<EmployeeByRole> employeeRolesList;
        List<EmployeeRoleDetails> employeeRoleDetails;
        if(ObjectUtils.isEmpty(roles)){
           employeeRoleDetails = roleAndSalaryService.findAllRoleAndSalary();
            employeeRolesList = Arrays.stream(DesignationType.values())
                    .map(role-> new EmployeeByRole( role.getValue(),
                            employeeRoleDetails.stream()
                                    .filter(employeeRoleDetail -> employeeRoleDetail.getRole().equals(role)).count()))
                    .collect(Collectors.toList());
        } else {
            employeeRoleDetails = roleAndSalaryService.getAllEmployeesByRole(roles);
            employeeRolesList = roles.stream()
                    .map(role -> new EmployeeByRole(role.getValue(),
                            employeeRoleDetails.stream().filter(employeeRoleDetails1 -> employeeRoleDetails1.getRole().equals(role)).count()))
                    .collect(Collectors.toList());
        }

        return new ResponseEntity(employeeRolesList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDetailsResponseDTO>> getEmployeeDetails(){
        return new ResponseEntity((employeeService.findAll()
                .stream().map(Employee::from)
                .collect(Collectors.toList())), HttpStatus.OK);
    }

    @PostMapping("/{id}/assign-role")
    public ResponseEntity assignRoleAndSalary(@RequestBody EmployeeRoleAndSalaryDTO employeeRoleAndSalaryDTO, @Valid @PathVariable String id){
        UUID employeeID = UUID.fromString(id);
        roleAndSalaryService.saveEmployeeRoleAndSalaryHistory(employeeID, employeeRoleAndSalaryDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/update-role-salary")
    public ResponseEntity<EmployeeRoleAndSalaryPatchDTO> updateRoleAndSalary(@RequestBody EmployeeRoleAndSalaryPatchDTO employeeRoleAndSalaryPatchDTO, @Valid @PathVariable String id){
        UUID employeeID = UUID.fromString(id);
        return new ResponseEntity(roleAndSalaryService.updateRoleAndSalary(employeeID, employeeRoleAndSalaryPatchDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}/role-salary-history")
    public ResponseEntity<List<EmployeeRoleAndSalaryResponseDTO>> getEmployeeHistory(@PathVariable UUID id){
        return new ResponseEntity(roleAndSalaryService.getEmployeeRoleAndSalaryHistory(id).stream()
                .map(EmployeeRoleAndSalary::to)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    private List<Employee> getEmployees(List<UUID> employeeIds){
        return !CollectionUtils.isEmpty(employeeIds) ? employeeService.getEmployees(employeeIds) : employeeService.findAll();
    }

}
