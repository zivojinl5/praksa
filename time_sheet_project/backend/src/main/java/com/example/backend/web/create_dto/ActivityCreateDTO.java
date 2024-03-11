package com.example.backend.web.create_dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ActivityCreateDTO {

    private LocalDate date;
    private String description;
    private double hours;
    private double overtimeHours;

    private Long clientId;
    private Long projectId;
    private Long categoryId;

}
