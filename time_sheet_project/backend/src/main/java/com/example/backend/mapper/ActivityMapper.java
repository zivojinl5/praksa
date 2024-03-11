package com.example.backend.mapper;

import com.example.backend.core.create_model.ActivityCreateModel;
import com.example.backend.core.model.Activity;
import com.example.backend.core.response_model.DayResponse;
import com.example.backend.core.response_model.MonthResponse;
import com.example.backend.core.search_model.ActivitySearchModel;
import com.example.backend.data.entity.ActivityEntity;
import com.example.backend.web.create_dto.ActivityCreateDTO;
import com.example.backend.web.dto.ActivityDTO;
import com.example.backend.web.response_dto.DayResponseDTO;
import com.example.backend.web.response_dto.MonthResponseDTO;
import com.example.backend.web.search_dto.ActivitySearchDTO;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class ActivityMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public ActivityDTO modelToDTO(Activity model) {
        return modelMapper.map(model, ActivityDTO.class);

    }

    public Activity dTOToModel(ActivityDTO dto) {
        return modelMapper.map(dto, Activity.class);
    }

    public ActivityEntity modelToEntity(Activity model) {
        return modelMapper.map(model, ActivityEntity.class);
    }

    public Activity entityToModel(ActivityEntity entity) {
        return modelMapper.map(entity, Activity.class);
    }

    public Activity createDTOToModel(ActivityCreateDTO createDTO) {
        return modelMapper.map(createDTO, Activity.class);
    }

    public ActivitySearchModel searchDTOToSearchModel(
            ActivitySearchDTO searchDTO) {
        return modelMapper.map(searchDTO, ActivitySearchModel.class);
    }

    public ActivityCreateModel createDTOToCreateModel(
            ActivityCreateDTO createDTO) {
        return modelMapper.map(createDTO, ActivityCreateModel.class);
    }

    public List<Activity> entitiesToModels(List<ActivityEntity> entities) {
        return entities.stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());
    }

    public ActivityEntity modelToEntityForPatch(Activity source, ActivityEntity destination) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(source, destination);
        // Reset skipNullEnabled to its default value
        modelMapper.getConfiguration().setSkipNullEnabled(false);
        return destination;
    }

    public Stream<Activity> streamEntityToStreamActivity(Stream<ActivityEntity> entityStream) {
        return entityStream.map(entity -> entityToModel(entity));
    }

    public DayResponseDTO dayResponseToDayResponseDTO(
            DayResponse dayResponse) {
        DayResponseDTO dayResponseDTO = modelMapper.map(dayResponse, DayResponseDTO.class);

        List<ActivityDTO> activityDTOs = dayResponse.getActivities().stream()
                .map(this::modelToDTO)
                .collect(Collectors.toList());
        dayResponseDTO.setActivityDTOs(activityDTOs);

        return dayResponseDTO;

    }

    public MonthResponseDTO monthResponseToMonthResponseDTO(MonthResponse monthResponse) {
        MonthResponseDTO monthResponseDTO = modelMapper.map(monthResponse, MonthResponseDTO.class);

        List<DayResponseDTO> dayResponseDTOs = monthResponse.getDays().stream()
                .map(this::dayResponseToDayResponseDTO)
                .collect(Collectors.toList());
        monthResponseDTO.setDays(dayResponseDTOs);

        return monthResponseDTO;
    }
}
