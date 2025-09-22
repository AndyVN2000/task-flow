package com.andy.task_flow.application.services;

import java.time.Clock;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.transaction.annotation.Transactional;

import com.andy.task_flow.application.data_transfer_objects.ProjectSummary;
import com.andy.task_flow.application.exceptions.ProjectNotFoundException;
import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.exceptions.ProjectAlreadyArchivedException;
import com.andy.task_flow.domain.repositories.ProjectRepository;

public class ProjectApplicationService {
    private final ProjectRepository projectRepository;

    public ProjectApplicationService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public void createProject(String title, String description) {
        Project project = ProjectImpl.of(title, description);
        projectRepository.save(project);
    }

    @Transactional
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

    @Transactional
    public void addTask(UUID projectId, Task task) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        project.addTask(task);
        projectRepository.save(project);
    }

    @Transactional
    public void removeTask(UUID projectId, UUID taskId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        project.removeTask(taskId);
        projectRepository.save(project);
    }

    public ProjectSummary getProjectDetails(UUID projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        
        return new ProjectSummary(
            projectId, 
            project.getName(), 
            project.getDescription(), 
            project.getCreatedAt(), 
            project.isArchived()
        );
    }

    // TODO: According to ChatGPT, the logic for filtering the list should be the responsibility of the  
    //   repository class. Meaning that ProjectRepository should have a 'findAllActiveProjects()' and
    //   'findAllArchivedProjects()' methods that does the filtering.
    public List<ProjectSummary> listActiveProjects() {
        List<Project> allProjects = projectRepository.findAll();
        Stream<Project> filteredProjects = allProjects.stream().filter(project -> !(project.isArchived()));
        return filteredProjects.map(project -> {
            return new ProjectSummary(
                project.getId(), 
                project.getName(), 
                project.getDescription(),
                project.getCreatedAt(),
                project.isArchived());
        }).toList();
    }

    public List<ProjectSummary> listArchivedProjects() {
        List<Project> allProjects = projectRepository.findAll();
        Stream<Project> filteredProjects = allProjects.stream().filter(project -> project.isArchived());
        return filteredProjects.map(project -> {
            return new ProjectSummary(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.isArchived()
            );
        }).toList();
    }

    public boolean hasOverdueTasks(UUID projectId, Clock clock) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        return project.hasOverdueTasks(clock);
    }

}
