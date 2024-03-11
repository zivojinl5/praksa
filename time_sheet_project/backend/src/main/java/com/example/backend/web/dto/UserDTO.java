package com.example.backend.web.dto;

import com.example.backend.enums.Role;
import com.example.backend.enums.UserStatus;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String name;
    private String userName;
    private String password;
    private String email;

    private Role role;
    private UserStatus userStatus;

}