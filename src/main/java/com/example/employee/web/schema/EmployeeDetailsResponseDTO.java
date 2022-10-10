package com.example.employee.web.schema;

import java.util.List;
import java.util.UUID;

public class EmployeeDetailsResponseDTO {

    private UUID employeeId;

    private NameDTO names;

    private String gender;

    private List<EmailDTO> email;

    private AddressDTO address;

    private String phone;

    private String dateOfBirth;


    public EmployeeDetailsResponseDTO(UUID employeeId, NameDTO names, String gender, List<EmailDTO> email, AddressDTO address, String phone, String dateOfBirth) {
        this.employeeId = employeeId;
        this.names = names;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }

    public EmployeeDetailsResponseDTO() {
    }

    public UUID getEmployeeId() {
        return employeeId;
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

    public static class Builder {

        private UUID employeeId;

        private NameDTO names;

        private String gender;

        private List<EmailDTO> emailDTO;

        private AddressDTO address;

        private String phoneNumber;

        private String dateOfBirth;

        private String designation;

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

        public EmployeeDetailsResponseDTO build(){
            return new EmployeeDetailsResponseDTO(this.employeeId, this.names, this.gender, this.emailDTO, this.address, this.phoneNumber, this.dateOfBirth);
        }
    }
}
