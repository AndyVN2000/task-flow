package com.andy.task_flow.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.andy.task_flow.application.exceptions.*;
import com.andy.task_flow.application.services.ProjectApplicationService;
import com.andy.task_flow.domain.entities.*;
import com.andy.task_flow.domain.entities.interfaces.*;
import com.andy.task_flow.domain.exceptions.DuplicateTaskException;
import com.andy.task_flow.domain.exceptions.ProjectAlreadyArchivedException;
import com.andy.task_flow.domain.repositories.ProjectRepository;
import com.andy.task_flow.fixtures.constants.TestConstant;
import com.andy.task_flow.fixtures.builders.*;;

@ExtendWith(MockitoExtension.class)
public class ProjectApplicationServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectApplicationService projectApplicationService;

    private TaskBuilder taskBuilder;
    private ProjectBuilder projectBuilder;

    @BeforeEach
    public void setUp() {
        taskBuilder = new TaskStubBuilder();
        projectBuilder = new ProjectImplBuilder();
    }

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
    @Test
    public void shouldThrowExceptionWhenArchivingAnArchivedProject() {
        // Setup
        UUID projectId = TestConstant.PROJECT_ID_0;
        String archivedBy = "John Doe";
        Clock clock = Clock.fixed(Instant.EPOCH, ZoneId.of(TestConstant.FIXED_ZONE_ID));
        Project archivedProject = ArchivedProject.of("Foo", "Bar", Instant.EPOCH, "Jane Doe", projectId);

        // Set the behavior of mock repository
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(archivedProject));

        // Execute user story and assert
        assertThrows(ProjectAlreadyArchivedException.class, 
            () -> projectApplicationService.archiveProject(projectId, archivedBy, clock));
        
        verify(projectRepository, never()).save(any());
    }

    // User adds a task to a project
    @Test
    public void shouldAddTaskToProject() {
        // Setup
        Project project = mock(Project.class);
        Task newTask = mock(Task.class);
        UUID projectId = TestConstant.PROJECT_ID_0;
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        
        // Execute user story
        projectApplicationService.addTask(projectId, newTask);

        // Assert
        verify(project).addTask(newTask);
        verify(projectRepository).save(project);
    }

    // User tries to add a task to a non-existent project
    @Test
    public void shouldThrowExceptionWhenAddingTaskToNonExistingProject() {
        // Setup
        Task newTask = mock(Task.class);
        UUID projectId = TestConstant.PROJECT_ID_0;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        
        // Execute user story and assert
        assertThrows(ProjectNotFoundException.class, 
            () -> projectApplicationService.addTask(projectId, newTask));
    }

    // User tries to add a task that already exists in the project
    @Test
    public void shouldThrowExceptionWhenAddingDuplicateTasks() {
        // Setup
        Task task0 = taskBuilder.setId(TestConstant.TASK_ID_0).build();
        Task task1 = taskBuilder.setId(TestConstant.TASK_ID_0).build();
        
        UUID projectId = TestConstant.PROJECT_ID_0;
        Project project = projectBuilder.addTask(task0).build();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Execute user story and assert
        assertThrows(DuplicateTaskException.class, 
            () -> projectApplicationService.addTask(projectId, task1));
    }

    // User removes a task from a project
    @Test
    public void shouldRemoveTask() {
        // Setup
        UUID taskId = TestConstant.TASK_ID_0;
        Task task = taskBuilder.setId(taskId).build();
        Project project = projectBuilder.addTask(task).build();
        Project projectSpy = spy(project);
        UUID projectId = TestConstant.PROJECT_ID_0;

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectSpy));

        // Execute user story
        projectApplicationService.removeTask(projectId, taskId);

        // Assert
        verify(projectSpy).removeTask(taskId);
        verify(projectRepository).save(projectSpy);
    }

    // User tries to remove a non-existent task from a project
    @Test
    public void shouldThrowExceptionWhenRemovingNonExistentTask() {
        // Setup
        UUID projectId = TestConstant.PROJECT_ID_0;
        UUID taskId = TestConstant.TASK_ID_0;
        Project project = projectBuilder.setId(projectId).build();
        Project projectSpy = spy(project);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectSpy));

        // Execute user story and assert
        assertThrows(TaskNotFoundException.class, 
            () -> projectApplicationService.removeTask(projectId, taskId)
        );
    }

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
