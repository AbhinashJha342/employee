package com.example.employee.web.schema;

import com.example.employee.domain.Address;
import com.example.employee.domain.AddressLines;

public class AddressDTO {

    private String city;

    private State state;

    private String postalCode;

    private AddressLinesDTO addressLines;

    private AddressDTO(String city, State state, String postalCode, AddressLinesDTO addressLines) {
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.addressLines = addressLines;
    }

    public AddressDTO() {
    }

    public String getCity() {
        return city;
    }

    public State getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public AddressLinesDTO getAddressLines() {
        return addressLines;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static Address to(AddressDTO addressDTO){
        return Address.builder().setCity(addressDTO.getCity()).setState(addressDTO.getState())
                .setPostalCode(addressDTO.getPostalCode())
                .setAddressLines(new AddressLines(addressDTO.getAddressLines().getExterior(),
                        addressDTO.getAddressLines().getInterior(), addressDTO.getAddressLines().getStreet())).build();
    }

    public static class Builder {

        private String city;

        private State state;

        private String postalCode;

        private AddressLinesDTO addressLines;

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

        public Builder setAddressLines(AddressLinesDTO addressLines) {
            this.addressLines = addressLines;
            return this;
        }

        public AddressDTO build(){
            return new AddressDTO(this.city, this.state, this.postalCode, this.addressLines);
        }
    }
}
