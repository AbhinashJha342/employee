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

    List<Employee> findAllByDeletedIsFalse();

    Optional<Employee> findEmployeesByEmployeeIdAndDeletedIsFalse(UUID employeeId);

    @Query(value = "select employee0_.id, employee0_.date_of_birth, \n" +
            "employee0_.deleted, employee0_.employee_id, employee0_.gender \n" +
            ",employee0_.name_id, name1_.first, name1_.last, employee0_.phone \n" +
            "from employee employee0_ left outer join employee_names name1_ on employee0_.name_id=name1_.id \n" +
            "where name1_.first ilike concat('%', :first, '%') OR name1_.last ilike concat('%', :last, '%') \n" +
            "and employee0_.deleted=false", nativeQuery = true)
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
