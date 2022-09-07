package com.example.employee.persistence;

import com.example.employee.domain.Employee;
import com.example.employee.domain.Name;
import com.example.employee.domain.NameFilter;
import com.example.employee.web.schema.DesignationType;
import com.example.employee.web.schema.State;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Employee save(Employee employee);

    //int countByAddress_State(State state);

    List<Employee> getEmployeeByAddress_State(State state);

    List<Employee> getEmployeeByEmployeeIdIn(List<UUID> employeeIds);

    Optional<List<Employee>> findAllByDeletedIsFalse();

    Employee findEmployeesByEmployeeIdAndDeletedIsFalse(UUID employeeId);

    List<Employee> findEmployeesByName_FirstContainingIgnoreCaseAndName_LastContainingIgnoreCase(@Param("first") String first, @Param("last") String last);

    List<Employee> findEmployeeByDesignation(DesignationType designation);

    @Query(value = "select emp from Employee emp Where (DATE_PART('doy', cast(:dateOfBirth as timestamp )) - DATE_PART('doy', cast(:nextDate as timestamp)))<= 7" +
            " OR (DATE_PART('doy', cast(:nextDate as timestamp)) - DATE_PART('doy', cast(:dateOfBirth as timestamp )))<= 7")
    List<Employee> findEmployeeByDateOfBirthBetween(ZonedDateTime dateOfBirth, ZonedDateTime nextDate);

//    @Query(value = "With filtered_employee_id( select * from address where state = :state ), Select * from employee where id in :filtered_employee_id")
//    int countByAddressState(String state);
}
