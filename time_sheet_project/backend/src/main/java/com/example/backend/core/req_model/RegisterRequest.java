package com.example.backend.core.req_model;

import com.example.backend.enums.Role;
import com.example.backend.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String userName;
    private String password;
    private String email;
    private UserStatus userStatus;
    private Role role;
}
