package com.example.backend.web.search_dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ActivitySearchDTO implements ISearchDTO {
    private Long userId;
    private LocalDate starDate;
    private LocalDate endDate;

}
