package com.andy.task_flow.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.interfaces.Project;

public interface ProjectRepository {

    AbstractProject save(AbstractProject project);

    Optional<AbstractProject> findById(UUID id);

    List<AbstractProject> findAll();

    List<AbstractProject> findActiveProjects();

    List<AbstractProject> findArchivedProjects();

    void delete(AbstractProject project);
    
}
