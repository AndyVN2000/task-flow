package com.andy.task_flow.application.services;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

import com.andy.task_flow.application.exceptions.ProjectNotFoundException;
import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.interfaces.MutableProject;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.exceptions.ProjectAlreadyArchivedException;
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
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        try {
            Project archivedProject = project.archive(archivedBy, clock);
            projectRepository.save(archivedProject);
        } catch (IllegalStateException e) {
            throw new ProjectAlreadyArchivedException(projectId.toString(), e);
        }
    }

    public void addTask(UUID projectId, Task task) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        project.addTask(task);
        projectRepository.save(project);
    }

    public void removeTask(UUID projectId, UUID taskId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        project.removeTask(taskId);
        projectRepository.save(project);
    }

    // TODO: This might be the where I need to use DTOs (Data Transfer Objects)
    public ProjectSummary getProjectDetails(UUID projectId) {
        
    }

    public List<Project> listActiveProjects() {

    }

    public List<Project> listArchivedProjects() {
        
    }

    public boolean hasOverdueTasks(UUID projectId, Clock clock) {
        
    }

}
