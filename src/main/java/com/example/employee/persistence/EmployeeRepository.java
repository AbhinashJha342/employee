package com.example.employee.persistence;

import com.example.employee.domain.Employee;
import com.example.employee.web.schema.State;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Employee save(Employee employee);

    //int countByAddress_State(State state);

    List<Employee> getEmployeeByAddress_State(State state);

    List<Employee> getEmployeeByDesignation(String designation);

    List<Employee> getEmployeeByEmployeeIdIn(List<UUID> employeeIds);

    List<Employee> findAllByDeletedIsFalse();

    Employee findEmployeesByEmployeeIdAndDeletedIsFalse(UUID employeeId);

//    @Query(value = "With filtered_employee_id( select * from address where state = :state ), Select * from employee where id in :filtered_employee_id")
//    int countByAddressState(String state);
}
