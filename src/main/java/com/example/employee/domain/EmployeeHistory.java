package com.example.employee.domain;

import com.example.employee.web.schema.EmployeeHistoryResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.UUID;

//@Entity
public class EmployeeHistory {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private Long id;

    @JsonProperty("old")
    private String before;

    @JsonProperty("new")
    private String after;

    public ZonedDateTime timeStamp;

    private UUID employeeID;

    public EmployeeHistory(String before, String after, ZonedDateTime timeStamp, UUID employeeID) {
        this.before = before;
        this.after = after;
        this.timeStamp = timeStamp;
        this.employeeID = employeeID;
    }

    public EmployeeHistory(){
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public UUID getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(UUID employeeID) {
        this.employeeID = employeeID;
    }

    public static EmployeeHistoryResponseDto from(EmployeeHistory employeeHistory){
        return new EmployeeHistoryResponseDto(employeeHistory.getEmployeeID(), employeeHistory.getBefore(),
                employeeHistory.getAfter(), employeeHistory.getTimeStamp());
    }
}
