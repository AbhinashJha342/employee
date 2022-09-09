package com.example.employee.persistence;

import com.example.employee.domain.Employee;
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

    Optional<List<Employee>> getEmployeeByAddress_StateAndDeletedFalse(State state);

    Optional<List<Employee>> getEmployeeByEmployeeIdInAndDeletedIsFalse(List<UUID> employeeIds);

    Optional<List<Employee>> findAllByDeletedIsFalse();

    Optional<Employee> findEmployeesByEmployeeIdAndDeletedIsFalse(UUID employeeId);

    Optional<List<Employee>> findEmployeesByName_FirstContainingIgnoreCaseAndName_LastContainingIgnoreCaseAndDeletedIsFalse(@Param("first") String first, @Param("last") String last);

    @Query(value = "select emp from Employee emp Where " +
            "DATE_PART('doy', cast(emp.dateOfBirth as timestamp)) - DATE_PART('doy', cast(:dateOfBirth as timestamp)) >= 0" +
            " AND DATE_PART('doy', cast(emp.dateOfBirth as timestamp)) - DATE_PART('doy', cast(:dateOfBirth as timestamp)) < 7" +
            " AND emp.deleted = cast(FALSE as boolean)")
    Optional<List<Employee>> findEmployeeByDateOfBirthBetween(ZonedDateTime dateOfBirth);

    Optional<List<Employee>> findEmployeeByGenderAndDeletedFalse(String gender);

//    @Query(value = "With filtered_employee_id( select * from address where state = :state ), Select * from employee where id in :filtered_employee_id")
//    int countByAddressState(String state);
}
