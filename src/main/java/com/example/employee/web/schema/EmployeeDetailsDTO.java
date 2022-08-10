package com.example.employee.web.schema;

import com.example.employee.domain.Email;
import com.example.employee.domain.Employee;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDetailsDTO {

    private final NameDTO names;

    private final String gender;

    private final List<EmailDTO> email;

    private final AddressDTO address;

    private final String phone;


    private EmployeeDetailsDTO(NameDTO names, String gender, List<EmailDTO> email, AddressDTO address, String phone) {
        this.names = names;
        this.gender = gender;
        this.email = CollectionUtils.isEmpty(email) ? new ArrayList<>() : email;
        this.address = address;
        this.phone = phone;
    }

    public NameDTO getNames() {
        return names;
    }

    public String getGender() {
        return gender;
    }

    public List<EmailDTO> getEmail() {
        return email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Builder builder(){
        return new Builder();
    }

    public static Employee to(EmployeeDetailsDTO employeeDetailsDTO){
        List<Email> emailList = Collections.emptyList();
        if(!employeeDetailsDTO.getEmail().isEmpty()){
            emailList = employeeDetailsDTO.getEmail().stream().map(EmailDTO::to).collect(Collectors.toList());
        }

        Employee emp = new Employee(employeeDetailsDTO.getPhone(), employeeDetailsDTO.getGender(),
                AddressDTO.to(employeeDetailsDTO.getAddress()), NameDTO.to(employeeDetailsDTO.getNames()), emailList);
        emp.getEmail().forEach(email1 -> email1.setEmployee(emp));
        return emp;
    }

    public static class Builder {

        private NameDTO names;

        private String gender;

        private List<EmailDTO> emailDTO;

        private AddressDTO address;

        private String phoneNumber;

        public Builder setNames(NameDTO names) {
            this.names = names;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setEmailDTO(List<EmailDTO> emailDTO) {
            this.emailDTO = emailDTO;
            return this;
        }

        public Builder setAddress(AddressDTO address) {
            this.address = address;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public EmployeeDetailsDTO build(){
            return new EmployeeDetailsDTO(this.names, this.gender, this.emailDTO, this.address, this.phoneNumber);
        }
    }
}
