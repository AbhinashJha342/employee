package com.example.employee.util;

import com.example.employee.web.schema.*;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

public class EmployeeProfileUtil {


    public static final MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));


    public static EmployeeDetailsRequestDTO createEmployeeRequest(){
        return EmployeeDetailsRequestDTO.builder().setEmployeeId(UUID.randomUUID()).setDateOfBirth("02-02-2022")
                .setAddress(employeeAddressDto())
                .setGender("female")
                .setPhoneNumber("5561124477")
                .setEmailDTO(List.of(employeeEmailDto()))
                .setNames(employeeNameDto()).build();
    }


    public static AddressDTO employeeAddressDto(){
        return AddressDTO.builder().setCity("Mx").setState(State.AG).setPostalCode("04230").setAddressLines(employeeAddressLinesDto()).build();
    }

    public static AddressLinesDTO employeeAddressLinesDto(){
        return AddressLinesDTO.builder().setExterior("117").setInterior("01").setStreet("Lx").build();
    }

    public static EmailDTO employeeEmailDto(){
        return EmailDTO.builder().setEmailAddress("test@test.com").setEmailType(EmailType.CORPORATE).build();
    }

    public static NameDTO employeeNameDto(){
        return new NameDTO("first", "last");
    }

    public static EmployeeDetailsRequestDTO createAnotherEmployeeRequest(){
        return EmployeeDetailsRequestDTO.builder().setEmployeeId(UUID.randomUUID()).setDateOfBirth("02-02-2022")
                .setAddress(employeeAddressDto())
                .setGender("female")
                .setPhoneNumber("5561124477")
                .setEmailDTO(List.of(anotherEmployeeEmailDto()))
                .setNames(employeeNameDto()).build();
    }

    public static EmailDTO anotherEmployeeEmailDto(){
        return EmailDTO.builder().setEmailAddress("test1@test.com").setEmailType(EmailType.CORPORATE).build();
    }

}
