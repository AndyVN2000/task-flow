package com.andy.task_flow.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.andy.task_flow.application.exceptions.ProjectNotFoundException;
import com.andy.task_flow.application.services.ProjectApplicationService;
import com.andy.task_flow.domain.entities.ArchivedProject;
import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.repositories.ProjectRepository;
import com.andy.task_flow.fixtures.constants.TestConstant;

@ExtendWith(MockitoExtension.class)
public class ProjectApplicationServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectApplicationService projectApplicationService;

    // User creates a project named 'Foo' with description 'Bar'
    @Test
    public void shouldCreateProject() {
        String title = "Foo";
        String description = "Bar";
        projectApplicationService.createProject(title, description);
        ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projectCaptor.capture());
        assertEquals(title, projectCaptor.getValue().getName());
        assertEquals(description, projectCaptor.getValue().getDescription());
    }

    // User archives a project
    @Test
    public void shouldArchiveAProject() {
        // Setup
        UUID projectId = TestConstant.PROJECT_ID_0;
        String archivedBy = "John Doe";
        Clock clock = Clock.fixed(Instant.EPOCH, ZoneId.of(TestConstant.FIXED_ZONE_ID));
        Project project = ProjectImpl.of("Foo", "Bar");

        // Set the behavior of the mock repository.
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Execute the user story
        projectApplicationService.archiveProject(projectId, archivedBy, clock);
        
        // Assertions
        ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projectCaptor.capture());

        Project savedProject = projectCaptor.getValue();
        assertTrue(savedProject.isArchived());
        assertTrue(savedProject instanceof ArchivedProject);
    }

    // User tries to archive a non-existent project
    @Test
    public void shouldThrowExceptionWhenArchivingNonExistingProject() {
        // Setup
        UUID projectId = TestConstant.PROJECT_ID_0;
        String archivedBy = "John Doe";
        Clock clock = Clock.fixed(Instant.EPOCH, ZoneId.of(TestConstant.FIXED_ZONE_ID));

        // Set behavior of mock repository
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Execute user story and assert
        assertThrows(ProjectNotFoundException.class, 
            () -> projectApplicationService.archiveProject(projectId, archivedBy, clock));

        verify(projectRepository, never()).save(any());
    }

    // User tries to archive a project that is already archived

    // User adds a task to a project

    // User tries to add a task to a non-existent project

    // User tries to add a task that already exists in the project

    // User removes a task from a project

    // User tries to remove a non-existent task from a project

    // User tries to remove a task from a non-existent project

    // User tries to remove a task that belongs to a different project

    // User tries to add or remove a task from an archived project (not allowed)

    // User accesses project details

    // User tries to access details on a non-existent project

    // User accesses details on an archived project

    // User accesses all active projects

    // User accesses all archived projects

    // User lists active projects when none exist (should return empty list)

    // User lists archived projects when none exist (should return empty list)

    // User queries whether a project has overdue tasks

    // User queries whether a non-existent project has overdue tasks

}
