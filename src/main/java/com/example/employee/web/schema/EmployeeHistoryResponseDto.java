package com.example.employee.web.schema;

import java.time.ZonedDateTime;
import java.util.UUID;

public class EmployeeHistoryResponseDto {

    private String before;

    private String after;

    public ZonedDateTime timeStamp;

    private UUID employeeID;

    public EmployeeHistoryResponseDto(UUID employeeID, String before, String after, ZonedDateTime timeStamp) {
        this.before = before;
        this.after = after;
        this.timeStamp = timeStamp;
        this.employeeID = employeeID;
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

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public UUID getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(UUID employeeID) {
        this.employeeID = employeeID;
    }
}
