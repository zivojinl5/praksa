package com.example.backend.web.req_dto;

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
public class RegisterRequestDTO {

  private String name;
  private String userName;
  private String password;
  private String email;
  private UserStatus userStatus;
  private Role role;
}

;