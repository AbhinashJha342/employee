package com.example.employee.web.schema;

import java.util.Arrays;
import java.util.stream.Stream;

public enum DesignationType {

    TRAINEE("Trainee"),
    SOFTWARE_ENGINEER("Software Engineer"),
    SENIOR_SOFTWARE_ENGINEER("Senior Software Engineer"),
    ARCHITECT("Architect"),
    MANAGER("Manager");

    private String value;

    DesignationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Stream<DesignationType> getRoles() {
       return Arrays.stream(DesignationType.values());
    }
}
