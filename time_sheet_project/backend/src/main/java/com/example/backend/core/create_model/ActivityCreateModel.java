
package com.example.backend.core.create_model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ActivityCreateModel {

    private LocalDate date;
    private String description;
    private double hours;
    private double overtimeHours;

    private Long clientId;
    private Long projectId;
    private Long categoryId;
    private Long userId;

}
