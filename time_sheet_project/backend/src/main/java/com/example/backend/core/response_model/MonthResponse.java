package com.example.backend.core.response_model;

import java.util.List;

import lombok.Data;

@Data
public class MonthResponse {
    private List<DayResponse> days;
    private double totalHours;

    // Custom constructor
    public MonthResponse(
            List<DayResponse> days) {
        this.days = days;
        setTotalHours();
    }

    public void setTotalHours() {
        double totalHours = 0.0;
        for (DayResponse day : days) {
            totalHours += day.getTotalHours();
        }
        this.totalHours = totalHours;
    }
}
