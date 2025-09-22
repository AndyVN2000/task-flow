package com.andy.task_flow.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.interfaces.Project;

public interface ProjectRepository {

    Project save(Project project);

    Optional<Project> findById(UUID id);

    List<Project> findAll();

    List<Project> findActiveProjects();

    List<Project> findArchivedProjects();

    void delete(Project project);
    
}
