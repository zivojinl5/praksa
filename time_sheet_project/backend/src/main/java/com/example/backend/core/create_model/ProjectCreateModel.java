package com.example.backend.core.create_model;

import lombok.Data;

@Data
public class ProjectCreateModel {

    private String name;
    private String description;
    private Long clientId;
    private Long teamMemberID;

}
