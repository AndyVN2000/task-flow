package com.andy.task_flow.application.services;

import java.time.Clock;
import java.util.UUID;

import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.interfaces.MutableProject;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.repositories.ProjectRepository;

public class ProjectApplicationService {
    private final ProjectRepository projectRepository;

    public ProjectApplicationService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void createProject(String title, String description) {
        Project project = ProjectImpl.of(title, description);
        projectRepository.save(project);
    }

    public void archiveProject(UUID projectId, String archivedBy, Clock clock) {
        MutableProject project = (MutableProject) projectRepository.findById(projectId).get();
        project.archive(archivedBy, clock);
    }

}
