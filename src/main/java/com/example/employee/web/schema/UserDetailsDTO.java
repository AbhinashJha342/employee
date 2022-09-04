package com.example.employee.web.schema;

import com.example.employee.domain.User;

import javax.validation.constraints.NotNull;

public class UserDetailsDTO {

    @NotNull(message = "username cannot be null")
    private final String username;

    @NotNull(message = "email cannot be null")
    private final String emailId;

    @NotNull(message = "password cannot be null")
    private final String password;

    @NotNull(message = "role cannot be null")
    private final String role;


    public UserDetailsDTO(String username, String emailId, String password, String role) {
        this.username = username;
        this.emailId = emailId;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public static User to(UserDetailsDTO userDetailsDTO){

        return User.builder().setEmail(userDetailsDTO.getEmailId()).setUsername(userDetailsDTO.getUsername())
                .setPassword(userDetailsDTO.getPassword()).setRole(userDetailsDTO.getRole()).build();
    }
}
