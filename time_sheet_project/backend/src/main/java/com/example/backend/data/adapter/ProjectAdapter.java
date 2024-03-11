package com.example.backend.data.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.backend.core.core_repository.IProjectCoreRepository;
import com.example.backend.core.create_model.ProjectCreateModel;
import com.example.backend.core.model.Project;
import com.example.backend.core.search_model.ProjectSearchModel;
import com.example.backend.data.entity.ClientEntity;
import com.example.backend.data.entity.ProjectEntity;
import com.example.backend.data.entity.UserEntity;
import com.example.backend.data.repository.IClientJPARepository;
import com.example.backend.data.repository.IProjectJPARepository;
import com.example.backend.data.repository.IUserJPARepository;
import com.example.backend.mapper.ProjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class ProjectAdapter implements IProjectCoreRepository {

    private final IProjectJPARepository projectJPARepository;

    private final IUserJPARepository teamMemberJPARepository;

    private final IClientJPARepository clientJPARepository;

    private final ProjectMapper mapper;

    @Override
    public Page<Project> searchProjects(ProjectSearchModel projectSearchModel, Pageable pageable) {
        Page<ProjectEntity> entitiesPage = projectJPARepository.searchClients(projectSearchModel, pageable);
        return mapEntitiesPageToModelsPage(entitiesPage);
    }

    @Override
    public HashMap<Long, String> getProjectNamesByClientId(Long clientId, Long teamMemberId) {
        return projectJPARepository.findProjectNamesAndIdsByClientIdAndTeamMemberId(clientId, teamMemberId);

    }

    @Override
    public Project findById(Long id) {
        ProjectEntity entity = projectJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        return (Project) mapper.mapEntityModelProject(entity);

    }

    @Override
    public Project save(ProjectCreateModel projectCreateModel) {

        ProjectEntity projectEntity = mapAndPopulateProjectEntity(projectCreateModel);

        // Save the project entity
        ProjectEntity createdEntity = projectJPARepository.save(projectEntity);

        // Map the created entity to a Project object and return it
        return mapper.mapEntityModelProject(createdEntity);
    }

    /*
     * @Override
     * public Project save(ProjectCreateModel projectCreateModel) {
     * ProjectEntity newProjectEntity =
     * mapper.mapCreateModelEntityProject(projectCreateModel);
     * 
     * Optional<ClientEntity> clientOptional =
     * clientJPARepository.findById(projectCreateModel.getClientId());
     * if (clientOptional.isPresent()) {
     * ClientEntity clientEntity = clientOptional.get();
     * newProjectEntity.setCustomer(clientEntity);
     * } else {
     * throw new NullPointerException("Client not found");
     * }
     * 
     * Optional<TeamMemberEntity> teamMemberOptional = teamMemberJPARepository
     * .findById(projectCreateModel.getClientId());
     * if (teamMemberOptional.isPresent()) {
     * TeamMemberEntity teamMemberEntity = teamMemberOptional.get();
     * newProjectEntity.setLead(teamMemberEntity);
     * } else {
     * // Handle the case when the country is not found
     * throw new NullPointerException("Team member not found");
     * }
     * 
     * ProjectEntity createdEntity = projectJPARepository.save(newProjectEntity);
     * return (Project) mapper.mapEntityModelProject(createdEntity);
     * }
     */
    @Override
    public Project update(Long id, Project model) {
        if (!projectJPARepository.existsById(id)) {
            return null;
        }
        ProjectEntity entity = (ProjectEntity) mapper.mapModelEntityProject(model);
        entity.setId(id);
        ProjectEntity updatedEntity = projectJPARepository.save(entity);
        return (Project) mapper.mapEntityModelProject(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        projectJPARepository.deleteById(id);
    }

    private Page<Project> mapEntitiesPageToModelsPage(Page<ProjectEntity> entitiesPage) {
        List<Project> projectList = entitiesPage.getContent().stream()
                .map(entity -> (Project) mapper.mapEntityModelProject(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(projectList, entitiesPage.getPageable(), entitiesPage.getTotalElements());
    }

    private ProjectEntity mapAndPopulateProjectEntity(ProjectCreateModel projectCreateModel) {
        // Create a new ProjectEntity instance
        ProjectEntity projectEntity = new ProjectEntity();

        // Populate fields from ProjectCreateModel
        projectEntity.setName(projectCreateModel.getName());
        projectEntity.setDescription(projectCreateModel.getDescription());

        // Fetch ClientEntity from the database using clientId
        ClientEntity clientEntity = clientJPARepository.findById(projectCreateModel.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        // Set the client for the project
        projectEntity.setCustomer(clientEntity);

        // Fetch TeamMemberEntity from the database using teamMemberID
        UserEntity teamMemberEntity = teamMemberJPARepository.findById(projectCreateModel.getTeamMemberID())
                .orElseThrow(() -> new IllegalArgumentException("Team member not found"));

        // Set the lead for the project
        projectEntity.setLead(teamMemberEntity);

        return projectEntity;
    }
}