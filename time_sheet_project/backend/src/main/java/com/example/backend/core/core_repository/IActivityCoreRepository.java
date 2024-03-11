package com.example.backend.core.core_repository;

import java.time.LocalDate;
import java.util.stream.Stream;

import com.example.backend.core.create_model.ActivityCreateModel;
import com.example.backend.core.model.Activity;
import com.example.backend.core.response_model.DayResponse;
import com.example.backend.core.search_model.ActivitySearchModel;

public interface IActivityCoreRepository {

    Activity findById(Long id);

    Activity save(ActivityCreateModel timeSheetEntryCreateModel);

    Activity update(Long id, Activity timeSheet);

    void deleteById(Long id);

    Stream<Activity> search(ActivitySearchModel searchModel);

    DayResponse findAllByDateAndUserId(LocalDate date, Long id);

}
