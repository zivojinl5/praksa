package com.example.backend.web.response_dto;

import java.util.List;

import lombok.Data;

@Data
public class MonthResponseDTO {
    private List<DayResponseDTO> days;
    private double totalHours;

}
