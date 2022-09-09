package com.example.employee.web.schema;

import com.example.employee.domain.Employee;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmployeeDetailsPatchRequestDTO {

    @NotNull(message = "name cannot be null.")
    private NameDTO names;

    private Optional<String> gender;

    private Optional<List<EmailDTO>> email;

    @NotNull(message = "address cannot be null.")
    private AddressDTO address;

    private Optional<String> phone;

    private Optional<String> dateOfBirth;


    public EmployeeDetailsPatchRequestDTO() {
    }


    public static Employee to(UUID employeeId, EmployeeDetailsPatchRequestDTO employeeDetailsPatchRequestDTO){
        return new Employee(employeeId, Optional.ofNullable(employeeDetailsPatchRequestDTO.getPhone()).isPresent() ? employeeDetailsPatchRequestDTO.getPhone().get() : "",
                Optional.ofNullable(employeeDetailsPatchRequestDTO.getGender()).isPresent() ? employeeDetailsPatchRequestDTO.getGender().get() : "",
                AddressDTO.to(employeeDetailsPatchRequestDTO.getAddress()),
                NameDTO.to(employeeDetailsPatchRequestDTO.getNames()),
                Optional.ofNullable(employeeDetailsPatchRequestDTO.getEmail()).isPresent() ?
                        employeeDetailsPatchRequestDTO.getEmail().get()
                                .stream()
                                .map(EmailDTO::to).collect(Collectors.toList()) : Collections.emptyList(),
                Optional.ofNullable(employeeDetailsPatchRequestDTO.getDateOfBirth()).isPresent() ? employeeDetailsPatchRequestDTO.getDateOfBirth().get() : "",
                false);
    }

    public NameDTO getNames() {
        return names;
    }

    public Optional<String> getGender() {
        return gender;
    }

    public Optional<List<EmailDTO>> getEmail() {
        return email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public Optional<String> getPhone() {
        return phone;
    }

    public Optional<String> getDateOfBirth() {
        return dateOfBirth;
    }

}
