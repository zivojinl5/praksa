package com.example.backend.web.create_dto;

import lombok.Data;

@Data
public class ProjectCreateDTO {
    private String name;
    private String description;
    private Long clientId;
    private Long teamMemberID;
}