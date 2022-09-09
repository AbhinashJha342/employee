package com.example.employee.web.schema;

import com.example.employee.domain.Address;
import com.example.employee.domain.Email;
import com.example.employee.domain.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeByState {

    private final NameDTO name;

    private final String phone;

    private final AddressDTO addressDTO;

    private final String gender;

    private final List<EmailDTO> emailDTO;

    public EmployeeByState(Employee employee) {
        this.name = new NameDTO(employee.getName().getFirst(), employee.getName().getLast());
        this.phone = employee.getPhone();
        this.addressDTO = Address.from(employee.getAddress());
        this.gender = employee.getGender();
        this.emailDTO = employee.getEmail().stream().map(Email::from).collect(Collectors.toList());
    }

    public NameDTO getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public String getGender() {
        return gender;
    }

    public List<EmailDTO> getEmailDTO() {
        return emailDTO;
    }
}
