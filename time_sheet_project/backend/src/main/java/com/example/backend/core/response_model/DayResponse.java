
package com.example.backend.core.response_model;

import java.time.LocalDate;
import java.util.List;

import com.example.backend.core.model.Activity;
import com.example.backend.enums.HoursStatus;

import lombok.Data;

@Data
public class DayResponse {
    private LocalDate date;
    private List<Activity> activities;
    private double totalHours;
    private HoursStatus statusHours;

    public DayResponse(LocalDate date, List<Activity> activities) {
        this.date = date;
        this.activities = activities;
        setTotalHours();
        setStatusHours();
    }

    public void setTotalHours() {
        double totalHours = 0.0;
        for (Activity activity : activities) {
            totalHours += activity.getHours() + activity.getOvertimeHours();
        }
        this.totalHours = totalHours;
    }

    public void setStatusHours() {
        if (totalHours == 8.0) {
            statusHours = HoursStatus.STANDARD;
        } else if (totalHours < 8.0) {
            statusHours = HoursStatus.LESS_THAN_STANDARD;
        } else {
            statusHours = HoursStatus.MORE_THAN_STANDARD;
        }
    }

}
