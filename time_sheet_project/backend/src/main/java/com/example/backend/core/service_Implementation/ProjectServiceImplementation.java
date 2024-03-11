package com.example.backend.core.service_Implementation;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.IProjectCoreRepository;
import com.example.backend.core.create_model.ProjectCreateModel;
import com.example.backend.core.model.Project;
import com.example.backend.core.search_model.ProjectSearchModel;
import com.example.backend.core.service.IProjectService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProjectServiceImplementation implements IProjectService {

    @Autowired
    private final IProjectCoreRepository coreProjectRepository;

    @Override
    public Page<Project> searchProjects(ProjectSearchModel projectSearchModel, Pageable pageable) {
        return coreProjectRepository.searchProjects(projectSearchModel, pageable);

    }

    @Override
    public HashMap<Long, String> getProjectNamesByClientId(Long clientId, Long teamMemberId) {
        return coreProjectRepository.getProjectNamesByClientId(clientId, teamMemberId);

    }

    @Override
    public Project getProjectById(Long id) {
        return coreProjectRepository.findById(id);
    }

    @Override
    public Project createProject(ProjectCreateModel projectCreateModel) {
        // Save the Project
        return coreProjectRepository.save(projectCreateModel);
    }

    @Override
    public Project updateProject(Long id, Project Project) {
        // Update the Project
        return coreProjectRepository.update(id, Project);
    }

    @Override
    public void deleteProject(Long id) {
        coreProjectRepository.deleteById(id);
    }

}
