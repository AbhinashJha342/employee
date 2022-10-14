package com.example.employee.domain;

import com.example.employee.web.schema.AddressDTO;
import com.example.employee.web.schema.State;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @Enumerated(EnumType.STRING)
    private State state;

    private String postalCode;

    @Embedded
    private AddressLines addressLines;

    @OneToOne
    @JoinColumn(name="employeeId")
    private Employee employee;

    public Address(String city, State state, String postalCode, AddressLines addressLines) {
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.addressLines = addressLines;
    }

    protected Address() {
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public AddressLines getAddressLines() {
        return addressLines;
    }

    public void setAddressLines(AddressLines addressLines) {
        this.addressLines = addressLines;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public static AddressDTO from(Address address){
        return AddressDTO.builder().setCity(address.getCity()).setPostalCode(address.getPostalCode())
                .setState(address.getState()).setAddressLines(AddressLines.from(address.getAddressLines())).build();
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private String city;

        private State state;

        private String postalCode;

        private AddressLines addressLines;

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setState(State state) {
            this.state = state;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setAddressLines(AddressLines addressLines) {
            this.addressLines = addressLines;
            return this;
        }

        public Address build(){
            return new Address(this.city, this.state, this.postalCode, this.addressLines);
        }
    }
}
