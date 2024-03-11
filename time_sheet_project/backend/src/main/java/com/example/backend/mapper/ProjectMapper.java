
package com.example.backend.mapper;

import com.example.backend.core.create_model.ProjectCreateModel;
import com.example.backend.core.model.Project;
import com.example.backend.core.search_model.ProjectSearchModel;
import com.example.backend.data.entity.ProjectEntity;
import com.example.backend.web.create_dto.ProjectCreateDTO;
import com.example.backend.web.dto.ProjectDTO;
import com.example.backend.web.search_dto.ProjectSearchDTO;

import lombok.NoArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class ProjectMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public ProjectDTO mapModelDTOProject(Project Project) {
        return modelMapper.map(Project, ProjectDTO.class);

    }

    public Project mapDTOModelProject(ProjectDTO dto) {
        return modelMapper.map(dto, Project.class);
    }

    public ProjectEntity mapModelEntityProject(Project Project) {
        return modelMapper.map(Project, ProjectEntity.class);
    }

    public Project mapEntityModelProject(ProjectEntity entity) {
        return modelMapper.map(entity, Project.class);
    }

    public ProjectCreateModel mapCreateDTOCreateModelProject(ProjectCreateDTO createDTO) {
        return modelMapper.map(createDTO, ProjectCreateModel.class);
    }

    public ProjectSearchModel mapSearchDTOSearchModelProject(ProjectSearchDTO projectSearchDTO) {
        return modelMapper.map(projectSearchDTO, ProjectSearchModel.class);

    }

    public ProjectEntity mapCreateModelEntityProject(ProjectCreateModel ProjectCreateModel) {
        return modelMapper.map(ProjectCreateModel, ProjectEntity.class);

    }

}
