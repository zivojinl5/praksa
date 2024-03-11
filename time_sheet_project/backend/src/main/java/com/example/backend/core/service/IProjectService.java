package com.example.backend.core.service;

import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.core.create_model.ProjectCreateModel;
import com.example.backend.core.model.Project;
import com.example.backend.core.search_model.ProjectSearchModel;

public interface IProjectService {
    Page<Project> searchProjects(ProjectSearchModel projectSearchModel, Pageable pageable);

    HashMap<Long, String> getProjectNamesByClientId(Long clientId, Long teamMemberId);

    Project getProjectById(Long id);

    Project createProject(ProjectCreateModel projectCreateModel);

    Project updateProject(Long id, Project project);

    void deleteProject(Long id);

}
