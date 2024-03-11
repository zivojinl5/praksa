package com.example.backend.web.response_dto;

import java.time.LocalDate;
import java.util.List;

import com.example.backend.enums.HoursStatus;
import com.example.backend.web.dto.ActivityDTO;

import lombok.Data;

@Data
public class DayResponseDTO {
    private LocalDate date;
    private List<ActivityDTO> activityDTOs;
    private double totalHours;
    private HoursStatus statusHours;
}
