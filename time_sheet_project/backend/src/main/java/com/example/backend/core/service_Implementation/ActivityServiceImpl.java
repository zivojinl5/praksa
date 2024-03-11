
package com.example.backend.core.service_Implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.IActivityCoreRepository;
import com.example.backend.core.create_model.ActivityCreateModel;
import com.example.backend.core.model.Activity;
import com.example.backend.core.response_model.DayResponse;
import com.example.backend.core.response_model.MonthResponse;
import com.example.backend.core.search_model.ActivitySearchModel;
import com.example.backend.core.service.IActivityService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private final IActivityCoreRepository coreRepository;

    @Override
    public Activity getById(Long id) {
        return coreRepository.findById(id);
    }

    @Override
    public Activity create(ActivityCreateModel createModel) {
        return coreRepository.save(createModel);

    }

    @Override
    public Activity update(Long id, Activity model) {
        return coreRepository.update(id, model);
    }

    @Override
    public void deleteById(Long id) {
        coreRepository.deleteById(id);

    }

    @Override
    public DayResponse getAllByDateAndUserId(LocalDate date, Long id) {
        return coreRepository.findAllByDateAndUserId(date, id);

    }

    @Override
    public MonthResponse search(ActivitySearchModel searchModel) {
        Stream<Activity> activityStream = coreRepository.search(searchModel);
        List<DayResponse> dayResponses = calculateDayResponses(activityStream);
        return new MonthResponse(dayResponses);
    }

    public static List<DayResponse> calculateDayResponses(Stream<Activity> activityStream) {
        Map<LocalDate, List<Activity>> activitiesByDate = activityStream
                .collect(Collectors.groupingBy(Activity::getDate));

        List<DayResponse> dayResponses = new ArrayList<>();
        activitiesByDate.forEach((date, activityList) -> {
            dayResponses.add(new DayResponse(date, activityList));
        });

        return dayResponses;
    }

}
