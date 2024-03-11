package com.example.backend.core.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Activity {
    private Long id;

    private LocalDate date;
    private String description;
    private double hours;
    private double overtimeHours;

    private Long clientId;
    private Long projectId;
    private Long categoryId;
    private Long userId;

}
