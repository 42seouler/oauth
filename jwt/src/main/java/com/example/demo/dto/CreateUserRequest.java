package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
public class CreateUserRequest {

    private String username;
    private String fullName;
    private String password;
    private String rePassword;
    private Set<String> authorities;
}
