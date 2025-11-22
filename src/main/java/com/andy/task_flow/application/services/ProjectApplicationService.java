package com.andy.task_flow.application.services;

import static org.mockito.ArgumentMatchers.booleanThat;

import java.time.Clock;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.transaction.annotation.Transactional;

import com.andy.task_flow.application.data_transfer_objects.ProjectSummary;
import com.andy.task_flow.application.exceptions.ProjectNotFoundException;
import com.andy.task_flow.application.exceptions.TaskNotFoundException;
import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.exceptions.DuplicateTaskException;
import com.andy.task_flow.domain.exceptions.ProjectAlreadyArchivedException;
import com.andy.task_flow.domain.repositories.ProjectRepository;

public class ProjectApplicationService {
    private final ProjectRepository projectRepository;

    public ProjectApplicationService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public void createProject(String title, String description) {
        AbstractProject project = ProjectImpl.of(title, description);
        projectRepository.save(project);
    }

    @Transactional
    public void archiveProject(UUID projectId, String archivedBy, Clock clock) {
        AbstractProject project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        
            AbstractProject archivedProject = project.archive(archivedBy, clock);
            projectRepository.save(archivedProject);
    }

    @Transactional
    public void addTask(UUID projectId, Task task) {
        AbstractProject project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        
        project.addTask(task);
        projectRepository.save(project);
    }

    @Transactional
    public void removeTask(UUID projectId, UUID taskId) {
        AbstractProject project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        boolean removed = project.removeTask(taskId);
        if (!removed) {
            throw new TaskNotFoundException(taskId.toString());
        }
        projectRepository.save(project);
    }

    public ProjectSummary getProjectDetails(UUID projectId) {
        AbstractProject project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        
        return new ProjectSummary(
            projectId, 
            project.getName(), 
            project.getDescription(), 
            project.getCreatedAt(), 
            project.isArchived()
        );
    }

    public List<ProjectSummary> listActiveProjects() {
        List<AbstractProject> activeProjects = projectRepository.findActiveProjects();
        return activeProjects.stream().map(project -> {
            return new ProjectSummary(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.isArchived()
            );
        }).toList();
    }

    public List<ProjectSummary> listArchivedProjects() {
        List<AbstractProject> archivedProjects = projectRepository.findArchivedProjects();
        return archivedProjects.stream().map(project -> {
            return new ProjectSummary(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.isArchived()
            );
        }).toList();
    }

    /*
     * The following methods are commented out. The filtering logic should be the responsibility of ProjectRepository.
     * Though I will keep this old, commented-out-code because I think my usage of Java Stream is cool.
     */
    // public List<ProjectSummary> listActiveProjects() {
    //     List<Project> allProjects = projectRepository.findAll();
    //     Stream<Project> filteredProjects = allProjects.stream().filter(project -> !(project.isArchived()));
    //     return filteredProjects.map(project -> {
    //         return new ProjectSummary(
    //             project.getId(), 
    //             project.getName(), 
    //             project.getDescription(),
    //             project.getCreatedAt(),
    //             project.isArchived());
    //     }).toList();
    // }

    // public List<ProjectSummary> listArchivedProjects() {
    //     List<Project> allProjects = projectRepository.findAll();
    //     Stream<Project> filteredProjects = allProjects.stream().filter(project -> project.isArchived());
    //     return filteredProjects.map(project -> {
    //         return new ProjectSummary(
    //             project.getId(),
    //             project.getName(),
    //             project.getDescription(),
    //             project.getCreatedAt(),
    //             project.isArchived()
    //         );
    //     }).toList();
    // }

    public boolean hasOverdueTasks(UUID projectId, Clock clock) {
        AbstractProject project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        return project.hasOverdueTasks(clock);
    }

}
