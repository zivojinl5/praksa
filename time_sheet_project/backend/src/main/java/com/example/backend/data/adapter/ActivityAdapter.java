package com.example.backend.data.adapter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.example.backend.core.core_repository.IActivityCoreRepository;
import com.example.backend.core.create_model.ActivityCreateModel;
import com.example.backend.core.model.Activity;
import com.example.backend.core.response_model.DayResponse;
import com.example.backend.core.response_model.MonthResponse;
import com.example.backend.core.search_model.ActivitySearchModel;
import com.example.backend.data.entity.CategoryEntity;
import com.example.backend.data.entity.ClientEntity;
import com.example.backend.data.entity.ProjectEntity;
import com.example.backend.data.entity.UserEntity;
import com.example.backend.data.entity.ActivityEntity;
import com.example.backend.data.repository.ICategoryJPARepository;
import com.example.backend.data.repository.IClientJPARepository;
import com.example.backend.data.repository.IProjectJPARepository;
import com.example.backend.data.repository.IUserJPARepository;
import com.example.backend.data.repository.IActivityJPARepository;
import com.example.backend.mapper.ActivityMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class ActivityAdapter implements IActivityCoreRepository {

    private final IActivityJPARepository jPARepository;
    private final IClientJPARepository clientJPARepository;
    private final IProjectJPARepository projectJPARepository;
    private final ICategoryJPARepository categoryJPARepository;
    private final IUserJPARepository teamMemberJPARepository;
    private final ActivityMapper mapper;

    @Override
    public Activity findById(Long id) {
        ActivityEntity entity = jPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));
        return mapper.entityToModel(entity);

    }

    @Override
    public Activity save(ActivityCreateModel timeSheetEntryCreateModel) {
        ActivityEntity entity = createTimeSheetEntry(timeSheetEntryCreateModel);
        ActivityEntity createdEntity = jPARepository.save(entity);
        return mapper.entityToModel(createdEntity);
    }

    @Override
    public Activity update(Long id, Activity details) {
        ActivityEntity target = jPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));
        target = mapper.modelToEntityForPatch(details, target);
        target.setId(id);
        ActivityEntity updatedEntity = jPARepository.save(target);

        return mapper.entityToModel(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jPARepository.deleteById(id);
    }

    public ActivityEntity createTimeSheetEntry(ActivityCreateModel entryCreateModel) {
        // Create a new TimeSheetEntryEntity instance
        ActivityEntity entryEntity = new ActivityEntity();

        // Populate fields from TimeSheetEntryCreateModel
        entryEntity.setDate(entryCreateModel.getDate());
        entryEntity.setDescription(entryCreateModel.getDescription());
        entryEntity.setHours(entryCreateModel.getHours());
        entryEntity.setOvertimeHours(entryCreateModel.getOvertimeHours());

        // Fetch ClientEntity from the database using clientId
        ClientEntity clientEntity = clientJPARepository.findById(entryCreateModel.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        // Set the client for the time sheet entry
        entryEntity.setClient(clientEntity);

        // Fetch ProjectEntity from the database using projectId
        ProjectEntity projectEntity = projectJPARepository.findById(entryCreateModel.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        // Set the project for the time sheet entry
        entryEntity.setProject(projectEntity);

        // Fetch CategoryEntity from the database using categoryId
        CategoryEntity categoryEntity = categoryJPARepository.findById(entryCreateModel.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // Set the category for the time sheet entry
        entryEntity.setCategory(categoryEntity);

        // Fetch TeamMemberEntity from the database using teamMemberId
        UserEntity userEntity = teamMemberJPARepository.findById(entryCreateModel.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Team member not found"));

        // Set the team member for the time sheet entry
        entryEntity.setUser(userEntity);

        // Return the created time sheet entry
        return entryEntity;
    }

    @Override
    public DayResponse findAllByDateAndUserId(LocalDate date, Long id) {
        List<ActivityEntity> entities = jPARepository.findAllByDateAndUserId(date, id);
        return new DayResponse(date, mapper.entitiesToModels(entities));
    }

    @Override
    public Stream<Activity> search(ActivitySearchModel searchModel) {
        Stream<ActivityEntity> entities = jPARepository.search(searchModel.getStarDate(), searchModel.getEndDate(),
                searchModel.getUserId());
        return mapper.streamEntityToStreamActivity(entities);

    }

}
