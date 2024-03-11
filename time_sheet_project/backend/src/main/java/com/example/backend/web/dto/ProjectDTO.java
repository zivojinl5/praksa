package com.example.backend.web.dto;

import lombok.Data;

@Data
public class ProjectDTO {
    private Long id;

    private String name;
    private String description;

    private ClientDTO customer;

    private UserDTO lead;

}
