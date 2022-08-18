package com.example.employee.domain;

import com.example.employee.web.schema.EmployeeDetailsDTO;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID employeeId;
    private String phone;
    private String gender;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "employee")
    private List<Email> email;

    @OneToOne(cascade = {CascadeType.PERSIST}, mappedBy = "employee")
    private Address address;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private Name name;

    private String dateOfBirth;

    private boolean isDeleted;

    public Employee(UUID employeeId, String phone, String gender, Address address, Name name, List<Email> email, String dateOfBirth, boolean isDeleted) {
        this.employeeId = employeeId;
        this.phone = phone;
        this.gender = gender;
        this.email = CollectionUtils.isEmpty(email) ? new ArrayList<>() : email;
        this.address = address;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.isDeleted = isDeleted;
    }

    public Employee() {
        this.email = new ArrayList<>();
    }


    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Email> getEmail() {
        return email;
    }

    public void setEmail(List<Email> email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public static EmployeeDetailsDTO from(Employee employee){
        return EmployeeDetailsDTO.builder().setEmployeeId(employee.getEmployeeId())
                .setNames(Name.from(employee.getName()))
                .setGender(employee.getGender()).setDateOfBirth(employee.getDateOfBirth()).setPhoneNumber(employee.getPhone())
                .setAddress(Address.from(employee.getAddress()))
                .setEmailDTO(employee.getEmail().stream().map(Email::from).collect(Collectors.toList()))
                .build();
    }
}
