package com.example.backend.core.core_repository;

import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.core.create_model.ProjectCreateModel;
import com.example.backend.core.model.Project;
import com.example.backend.core.search_model.ProjectSearchModel;

public interface IProjectCoreRepository {

    Page<Project> searchProjects(ProjectSearchModel projectSearchModel, Pageable pageable);

    HashMap<Long, String> getProjectNamesByClientId(Long clientId, Long teamMemberId);

    Project findById(Long id);

    Project save(ProjectCreateModel projectCreateModel);

    Project update(Long id, Project project);

    void deleteById(Long id);

}