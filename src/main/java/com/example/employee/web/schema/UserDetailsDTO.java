package com.example.employee.web.schema;

import com.example.employee.domain.UserAndRole;
import javax.validation.constraints.NotNull;

public class UserDetailsDTO {

    @NotNull(message = "username cannot be null")
    private final String username;

    @NotNull(message = "password cannot be null")
    private final String password;

    @NotNull(message = "role cannot be null")
    private final String role;


    public UserDetailsDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public static UserAndRole to(UserDetailsDTO userDetailsDTO){
        return new UserAndRole(userDetailsDTO.getUsername(), userDetailsDTO.getPassword(), userDetailsDTO.getRole());
    }
}
