package com.example.employee.web.schema;

import com.example.employee.domain.Address;
import com.example.employee.domain.Email;
import com.example.employee.domain.Employee;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmployeeDetailsRequestDTO {

    @NotNull(message = "name cannot be null.")
    private final NameDTO names;

    private final String gender;

    private final List<EmailDTO> email;

    @NotNull(message = "address cannot be null.")
    private final AddressDTO address;

    private final String phone;

    private final String dateOfBirth;

    private EmployeeDetailsRequestDTO(NameDTO names, String gender, List<EmailDTO> email, AddressDTO address, String phone, String dateOfBirth) {
        this.names = names;
        this.gender = gender;
        this.email = CollectionUtils.isEmpty(email) ? new ArrayList<>() : email;
        this.address = address;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static Employee to(UUID employeeId, EmployeeDetailsRequestDTO employeeDetailsRequestDTO){
        List<Email> emailList = Collections.emptyList();
        if(!employeeDetailsRequestDTO.getEmail().isEmpty()){
            emailList = employeeDetailsRequestDTO.getEmail().stream().map(EmailDTO::to).collect(Collectors.toList());
        }

        Address address = AddressDTO.to(employeeDetailsRequestDTO.getAddress());

        Employee emp = new Employee(employeeId, employeeDetailsRequestDTO.getPhone(), employeeDetailsRequestDTO.getGender(),
                address, NameDTO.to(employeeDetailsRequestDTO.getNames()), emailList, employeeDetailsRequestDTO.getDateOfBirth(), false);
        //emp.getEmail().forEach(email1 -> email1.setEmployee(emp));
        emp.getAddress().setEmployee(emp);
        return emp;
    }

    public static class Builder {

        private UUID employeeId;

        private NameDTO names;

        private String gender;

        private List<EmailDTO> emailDTO;

        private AddressDTO address;

        private String phoneNumber;

        private String dateOfBirth;

        private DesignationType designation;

        private String salary;

        public Builder setEmployeeId(UUID employeeId) {
            this.employeeId = employeeId;
            return this;
        }

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

        public Builder setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public EmployeeDetailsRequestDTO build(){
            return new EmployeeDetailsRequestDTO(this.names, this.gender, this.emailDTO, this.address, this.phoneNumber, this.dateOfBirth);
        }
    }
}
