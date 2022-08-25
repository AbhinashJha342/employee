package com.example.employee.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Change {

    private String designation;

    private String salary;

    public Change(String designation, String salary) {
        this.designation = designation;
        this.salary = salary;
    }

    public Change() {}

    public String getDesignation() {
        return designation;
    }

    public String getSalary() {
        return salary;
    }

    public static String jsonTransformation(ObjectMapper objectMapper, TypeReference<HashMap<String, Object>> typeReference, Change change) throws JsonProcessingException {
        Map<String, Object> existingTaskMap = objectMapper.readValue(change!=null ? objectMapper.writeValueAsString(change) : "{}", typeReference);
        return objectMapper.writeValueAsString(existingTaskMap);
    }


}
