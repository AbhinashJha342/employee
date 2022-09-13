package com.example.employee.service.impl;

import com.example.employee.domain.Employee;
import com.example.employee.domain.EmployeeRoleAndSalary;
import com.example.employee.domain.EmployeeSalaryAndDifferenceView;
import com.example.employee.exception.DataAlreadyExistsException;
import com.example.employee.exception.DbNotUpdatedException;
import com.example.employee.exception.NotFoundException;
import com.example.employee.persistence.EmployeeRepository;
import com.example.employee.persistence.EmployeeRoleAndSalaryRepository;
import com.example.employee.service.RoleAndSalaryService;
import com.example.employee.web.schema.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleAndSalaryServiceImpl implements RoleAndSalaryService {

    private final EmployeeRoleAndSalaryRepository employeeRoleAndSalaryRepository;

    private final ObjectMapper objectMapper;

    private final TypeReference<HashMap<String, Object>> typeReference;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public RoleAndSalaryServiceImpl(EmployeeRoleAndSalaryRepository employeeHistoryRepository, ObjectMapper objectMapper, TypeReference typeReference, EmployeeRepository employeeRepository) {
        this.employeeRoleAndSalaryRepository = employeeHistoryRepository;
        this.objectMapper = objectMapper;
        this.typeReference = typeReference;
        this.employeeRepository = employeeRepository;
    }

    /*@Override
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
    }*/

    @Override
    public void saveEmployeeRoleAndSalaryHistory(UUID employeeID, EmployeeRoleAndSalaryDTO employeeRoleAndSalaryDTO) {
        employeeRepository.findEmployeesByEmployeeIdAndDeletedIsFalse(employeeID).orElseThrow(()-> new NotFoundException("No employee exists with employeeId "+employeeID));;
        Optional<EmployeeRoleAndSalary> employeeRoleAndSalary = employeeRoleAndSalaryRepository.getAllByEmployeeIdAndEndDateIsNull(employeeID);
        if(employeeRoleAndSalary.isPresent())
            throw new DataAlreadyExistsException("there is already an existing role for this employee "+employeeID);
        employeeRoleAndSalaryRepository.save(EmployeeRoleAndSalaryDTO.to(employeeRoleAndSalaryDTO, employeeID));
    }

    @Override
    public List<EmployeeRoleAndSalary> getEmployeeRoleAndSalaryHistory(UUID employeeId) {
        return employeeRoleAndSalaryRepository.getAllByEmployeeId(employeeId);
    }

    @Override
    public EmployeeRoleAndSalaryPatchDTO updateRoleAndSalary(UUID employeeId, EmployeeRoleAndSalaryPatchDTO employeeRoleAndSalaryPatchDTO) {
        employeeRepository.findEmployeesByEmployeeIdAndDeletedIsFalse(employeeId).orElseThrow(()-> new NotFoundException("no employee exists with employeeID "+employeeId));
        Optional<EmployeeRoleAndSalary> roleAndSalaryHistory = employeeRoleAndSalaryRepository.getAllByEmployeeIdAndEndDateIsNull(employeeId);
        roleAndSalaryHistory.orElseThrow(()-> new NotFoundException("No roles assigned to "+employeeId));
        EmployeeRoleAndSalary currentRoleAndSalary = roleAndSalaryHistory.get();
        if(employeeRoleAndSalaryPatchDTO.canBeUpdated(currentRoleAndSalary.getEndDate(), currentRoleAndSalary.getRole(), currentRoleAndSalary.getStartDate())){
            currentRoleAndSalary.setSalary(employeeRoleAndSalaryPatchDTO.getSalary().get());
            currentRoleAndSalary.setRole(employeeRoleAndSalaryPatchDTO.getRole());
            currentRoleAndSalary.setEndDate(employeeRoleAndSalaryPatchDTO.getEndDate().get());
            employeeRoleAndSalaryRepository.save(currentRoleAndSalary);
            return employeeRoleAndSalaryPatchDTO;
        } else {
            throw new DbNotUpdatedException("The role and salary of the employee could not be updated");
        }
    }

    @Override
    public List<EmployeeRoleDetails> getAllEmployeesByRole(List<DesignationType> role) {
        List<EmployeeRoleAndSalary> employeeRoleAndSalaries = employeeRoleAndSalaryRepository.getAllByRoleInAndEndDateIsNull(role);
        List<UUID> employeeIDs = employeeRoleAndSalaries.stream().map(employeeRoleAndSalary -> employeeRoleAndSalary.getEmployeeId()).collect(Collectors.toList());
        List<Employee> employeeList = employeeRepository.getEmployeeByEmployeeIdInAndDeletedIsFalse(employeeIDs).orElseThrow(()-> new NotFoundException("There are no employees with the roles mentioned"));

        return employeeList.stream().flatMap(employee -> employeeRoleAndSalaries.stream()
                        .filter(employeeRoleAndSalary -> employeeRoleAndSalary.getEmployeeId().toString().equals(employee.getEmployeeId().toString()))
                        .map(roleAndSalary -> new EmployeeRoleDetails(employee, roleAndSalary)))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeRoleDetails> findAllRoleAndSalary() {
        List<EmployeeRoleAndSalary> employeeRoleAndSalaries = employeeRoleAndSalaryRepository.findAllByEndDateIsNull();
        List<UUID> employeeIDs = employeeRoleAndSalaries.stream().map(employeeRoleAndSalary -> employeeRoleAndSalary.getEmployeeId()).collect(Collectors.toList());
        return employeeRepository.getEmployeeByEmployeeIdInAndDeletedIsFalse(employeeIDs)
                .orElseThrow(()-> new NotFoundException("There are no employees with the roles mentioned"))
                .stream().flatMap(employee -> employeeRoleAndSalaries.stream().filter(employeeRoleAndSalary ->
                        employeeRoleAndSalary.getEmployeeId().toString().equals(employee.getEmployeeId().toString()))
                        .map(roleAndSalary -> new EmployeeRoleDetails(employee, roleAndSalary)))
                .collect(Collectors.toList());
    }

    @Override
    public void updateEndDate(UUID employeeID) {
        Optional<EmployeeRoleAndSalary> employeeRoleAndSalary = employeeRoleAndSalaryRepository.getAllByEmployeeIdAndEndDateIsNull(employeeID);
        if(employeeRoleAndSalary.isPresent()){
            employeeRoleAndSalary.get().setEndDate(ZonedDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
            employeeRoleAndSalaryRepository.save(employeeRoleAndSalary.get());
        }
    }

    @Override
    public List<EmployeeSalaryCount> getSalaryDiff(int set) {
        Optional.of(employeeRoleAndSalaryRepository.findAllByEndDateIsNull()).orElseThrow(()-> new NotFoundException("no employee have role and salary assigned."));
        EmployeeSalaryAndDifferenceView employeeRoleView = employeeRoleAndSalaryRepository.getEmployeeMinMaxSalaryAndDiff();
        int range = employeeRoleView.getSalaryDiff()/set;
        int min = employeeRoleView.getMinSalary();
        int max = employeeRoleView.getMaxSalary();
        int tempMax = min+range;
        List<EmployeeSalaryCount> salaryCounts = new ArrayList<>();

        for(int i = 1; i<=set; i++){
            if(i==1){
                List<EmployeeRoleAndSalary> employeeRoleAndSalaries = employeeRoleAndSalaryRepository.getEmployeesInSalaryRange(min, tempMax);
                salaryCounts.add(new EmployeeSalaryCount(employeeRoleAndSalaries.size(), min+"-"+tempMax));
            } else if (i == set){
                List<EmployeeRoleAndSalary> employeeRoleAndSalaries = employeeRoleAndSalaryRepository.getEmployeesInSalaryRange(tempMax, max);
                salaryCounts.add(new EmployeeSalaryCount(employeeRoleAndSalaries.size(), tempMax+"-"+max));
            } else{
                List<EmployeeRoleAndSalary> employeeRoleAndSalaries = employeeRoleAndSalaryRepository.getEmployeesInSalaryRange(tempMax, min+(range*i));
                salaryCounts.add(new EmployeeSalaryCount(employeeRoleAndSalaries.size(), tempMax+"-"+(min+(range*i))));
                tempMax = min+(range*i);
            }
            tempMax+=1;
        }
        return salaryCounts;
    }
}
