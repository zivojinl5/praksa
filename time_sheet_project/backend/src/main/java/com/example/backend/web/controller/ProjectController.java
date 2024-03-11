package com.example.backend.web.controller;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.backend.config.JwtService;
import com.example.backend.core.create_model.ProjectCreateModel;
import com.example.backend.core.model.Project;
import com.example.backend.core.search_model.ProjectSearchModel;
import com.example.backend.core.service.IProjectService;
import com.example.backend.mapper.ProjectMapper;
import com.example.backend.web.create_dto.ProjectCreateDTO;
import com.example.backend.web.dto.ProjectDTO;
import com.example.backend.web.search_dto.ProjectSearchDTO;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/projects")
@PreAuthorize("hasRole('ADMIN')")
public class ProjectController {
    private final IProjectService projectService;
    private final JwtService jwtService;
    private final ProjectMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable("id") Long id) {
        Project foundProject = projectService.getProjectById(id);
        ProjectDTO foundProjectDTO = (ProjectDTO) mapper.mapModelDTOProject(foundProject);
        return ResponseEntity.ok(foundProjectDTO);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectCreateDTO createProjectDTO) {
        ProjectCreateModel projectCreateModel = mapper.mapCreateDTOCreateModelProject(createProjectDTO);
        Project createdProject = projectService.createProject(projectCreateModel);
        ProjectDTO createdProjectDTO = mapper.mapModelDTOProject(createdProject);
        return new ResponseEntity<>(createdProjectDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable("id") Long id,
            @RequestBody ProjectDTO details) {
        Project Project = (com.example.backend.core.model.Project) mapper.mapDTOModelProject(details);
        Project.setId(id);
        Project updatedProject = projectService.updateProject(id, Project);

        ProjectDTO updatedProjectDTO = (ProjectDTO) mapper.mapModelDTOProject(updatedProject);
        return ResponseEntity.ok(updatedProjectDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok("Project deleted");
    }

    private Page<ProjectDTO> mapModelsPageDTOsPage(Page<Project> models) {
        List<ProjectDTO> ProjectDTOList = models.getContent().stream()
                .map(model -> (ProjectDTO) mapper.mapModelDTOProject(model))
                .collect(Collectors.toList());

        return new PageImpl<>(ProjectDTOList, models.getPageable(), models.getTotalElements());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProjectDTO>> searchProjects(
            @RequestBody ProjectSearchDTO projectSearchDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        ProjectSearchModel projectSearchModel = (ProjectSearchModel) mapper
                .mapSearchDTOSearchModelProject(projectSearchDTO);
        Page<Project> projectsPage = projectService.searchProjects(projectSearchModel, pageable);
        Page<ProjectDTO> projectDTOsPage = mapModelsPageDTOsPage(projectsPage);

        return ResponseEntity.ok(projectDTOsPage);
    }

    @GetMapping("/names")
    public ResponseEntity<HashMap<Long, String>> getProjectNamesByClientId(
            @RequestBody Long clientId,
            @RequestHeader("Authorization") String token) {
        Long userId = jwtService.extractId(token);
        HashMap<Long, String> projects = projectService.getProjectNamesByClientId(clientId, userId);
        return ResponseEntity.ok(projects);
    }

}
