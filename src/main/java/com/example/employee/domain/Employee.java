package com.example.employee.domain;

import com.example.employee.web.schema.EmployeeDetailsResponseDTO;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID employeeId;
    private String phone;
    private String gender;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name="email_id")
    private Collection<Email> email;

    @OneToOne(cascade = {CascadeType.PERSIST}, mappedBy = "employee", orphanRemoval = true)
    private Address address;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Name name;

    private String dateOfBirth;

    private boolean deleted;

    public Employee(UUID employeeId, String phone, String gender, Address address, Name name, List<Email> email, String dateOfBirth, boolean deleted) {
        this.employeeId = employeeId;
        this.phone = phone;
        this.gender = gender;
        this.email = CollectionUtils.isEmpty(email) ? new ArrayList<>() : email;
        this.address = address;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.deleted = deleted;
    }

    protected Employee() {}


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

    public Collection<Email> getEmail() {
        return email;
    }

    public void setEmail(Collection<Email> email) {
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
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public static EmployeeDetailsResponseDTO from(Employee employee){
        return EmployeeDetailsResponseDTO.builder().setEmployeeId(employee.getEmployeeId())
                .setNames(Name.from(employee.getName()))
                .setGender(employee.getGender()).setDateOfBirth(employee.getDateOfBirth()).setPhoneNumber(employee.getPhone())
                .setAddress(Address.from(employee.getAddress()))
                .setEmailDTO(employee.getEmail().stream().map(Email::from).collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return deleted == employee.deleted && Objects.equals(id, employee.id) && Objects.equals(employeeId, employee.employeeId) && Objects.equals(phone, employee.phone) && Objects.equals(gender, employee.gender) && Objects.equals(email, employee.email) && Objects.equals(address, employee.address) && Objects.equals(name, employee.name) && Objects.equals(dateOfBirth, employee.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, phone, gender, email, address, name, dateOfBirth, deleted);
    }
}
