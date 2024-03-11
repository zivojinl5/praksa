package com.example.backend.core.service;

import java.time.LocalDate;

import com.example.backend.core.create_model.ActivityCreateModel;
import com.example.backend.core.model.Activity;
import com.example.backend.core.response_model.DayResponse;
import com.example.backend.core.response_model.MonthResponse;
import com.example.backend.core.search_model.ActivitySearchModel;

public interface IActivityService {

    Activity getById(Long id);

    Activity create(ActivityCreateModel timeSheetEntryCreateModel);

    Activity update(Long id, Activity timeSheetEntry);

    void deleteById(Long id);

    MonthResponse search(
            ActivitySearchModel timeSheetEntrySearchModel);

    DayResponse getAllByDateAndUserId(LocalDate date, Long teamMemberId);

}
