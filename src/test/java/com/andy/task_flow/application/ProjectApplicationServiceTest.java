package com.andy.task_flow.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.andy.task_flow.application.data_transfer_objects.ProjectSummary;
import com.andy.task_flow.application.exceptions.*;
import com.andy.task_flow.application.services.ProjectApplicationService;
import com.andy.task_flow.domain.entities.*;
import com.andy.task_flow.domain.entities.interfaces.*;
import com.andy.task_flow.domain.enums.TaskStatus;
import com.andy.task_flow.domain.exceptions.ChangeNotAllowedException;
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
    private final UUID projectId0 = TestConstant.PROJECT_ID_0;
    private final UUID taskId0 = TestConstant.TASK_ID_0;

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
        String archivedBy = "John Doe";
        Clock clock = Clock.fixed(Instant.EPOCH, ZoneId.of(TestConstant.FIXED_ZONE_ID));
        Project project = ProjectImpl.of("Foo", "Bar");

        // Set the behavior of the mock repository.
        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(project));

        // Execute the user story
        projectApplicationService.archiveProject(projectId0, archivedBy, clock);
        
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
        String archivedBy = "John Doe";
        Clock clock = Clock.fixed(Instant.EPOCH, ZoneId.of(TestConstant.FIXED_ZONE_ID));

        // Set behavior of mock repository
        when(projectRepository.findById(projectId0)).thenReturn(Optional.empty());

        // Execute user story and assert
        assertThrows(ProjectNotFoundException.class, 
            () -> projectApplicationService.archiveProject(projectId0, archivedBy, clock));

        verify(projectRepository, never()).save(any());
    }

    // User tries to archive a project that is already archived
    @Test
    public void shouldThrowExceptionWhenArchivingAnArchivedProject() {
        // Setup
        String archivedBy = "John Doe";
        Clock clock = Clock.fixed(Instant.EPOCH, ZoneId.of(TestConstant.FIXED_ZONE_ID));
        Project archivedProject = ArchivedProject.of("Foo", "Bar", Instant.EPOCH, "Jane Doe", projectId0);

        // Set the behavior of mock repository
        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(archivedProject));

        // Execute user story and assert
        assertThrows(ProjectAlreadyArchivedException.class, 
            () -> projectApplicationService.archiveProject(projectId0, archivedBy, clock));
        
        verify(projectRepository, never()).save(any());
    }

    // User adds a task to a project
    @Test
    public void shouldAddTaskToProject() {
        // Setup
        Project project = mock(Project.class);
        Task newTask = mock(Task.class);
        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(project));
        
        // Execute user story
        projectApplicationService.addTask(projectId0, newTask);

        // Assert
        verify(project).addTask(newTask);
        verify(projectRepository).save(project);
    }

    // User tries to add a task to a non-existent project
    @Test
    public void shouldThrowExceptionWhenAddingTaskToNonExistingProject() {
        // Setup
        Task newTask = mock(Task.class);
        when(projectRepository.findById(projectId0)).thenReturn(Optional.empty());
        
        // Execute user story and assert
        assertThrows(ProjectNotFoundException.class, 
            () -> projectApplicationService.addTask(projectId0, newTask));
    }

    // User tries to add a task that already exists in the project
    @Test
    public void shouldThrowExceptionWhenAddingDuplicateTasks() {
        // Setup
        Task task0 = taskBuilder.setId(TestConstant.TASK_ID_0).build();
        Task task1 = taskBuilder.setId(TestConstant.TASK_ID_0).build();
        
        Project project = projectBuilder.addTask(task0).build();
        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(project));

        // Execute user story and assert
        assertThrows(DuplicateTaskException.class, 
            () -> projectApplicationService.addTask(projectId0, task1));
    }

    // User removes a task from a project
    @Test
    public void shouldRemoveTask() {
        // Setup
        UUID taskId = TestConstant.TASK_ID_0;
        Task task = taskBuilder.setId(taskId).build();
        Project project = projectBuilder.addTask(task).build();
        Project projectSpy = spy(project);

        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(projectSpy));

        // Execute user story
        projectApplicationService.removeTask(projectId0, taskId);

        // Assert
        verify(projectSpy).removeTask(taskId);
        verify(projectRepository).save(projectSpy);
    }

    // User tries to remove a non-existent task from a project
    @Test
    public void shouldThrowExceptionWhenRemovingNonExistentTask() {
        // Setup
        UUID taskId = TestConstant.TASK_ID_0;
        Project project = projectBuilder.setId(projectId0).build();
        Project projectSpy = spy(project);

        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(projectSpy));

        // Execute user story and assert
        assertThrows(TaskNotFoundException.class, 
            () -> projectApplicationService.removeTask(projectId0, taskId)
        );
    }

    // User tries to remove a task from a non-existent project
    @Test
    public void shouldThrowExceptionWhenRemovingTaskFromNonExistentProject() {
        // Setup
        UUID taskId = TestConstant.TASK_ID_0;
        when(projectRepository.findById(projectId0)).thenReturn(Optional.empty());

        // Execute user story and assert
        assertThrows(ProjectNotFoundException.class,
            () -> projectApplicationService.removeTask(projectId0, taskId)
        );
    }

    // User tries to remove a task that belongs to a different project
    @Test
    public void shouldThrowExceptionWhenInvokingProjectToDeleteTaskInAnotherProject() {
        // Setup
        Project invokedProject = projectBuilder.setId(projectId0).build();
        Project otherProject = projectBuilder.setId(TestConstant.PROJECT_ID_1).build();
        Task taskToRemove = taskBuilder.setId(taskId0)
            .setProject(otherProject)
            .build();
        otherProject.addTask(taskToRemove);
        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(invokedProject));

        // Assert
        assertThrows(TaskNotFoundException.class,
            () -> projectApplicationService.removeTask(projectId0, taskId0)
        );
    }

    // User tries to add or remove a task from an archived project (not allowed)
    @Test
    public void shouldThrowExceptionWhenRemovingTaskFromArchivedProject() {
        // Setup
        ProjectBuilder archivedBuilder = new ArchivedProject.ArchivedProjectBuilder();
        Task task = taskBuilder.setId(taskId0).build();
        Project archivedProject = archivedBuilder.setId(projectId0)
            .addTask(task)
            .build();
        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(archivedProject));

        // Assert
        assertThrows(ChangeNotAllowedException.class, 
            () -> projectApplicationService.removeTask(projectId0, taskId0)
        );
    }

    // User accesses project details
    @Test
    public void shouldReturnProjectSummary() {
        // Setup
        Project project = projectBuilder.setId(projectId0)
            .setName("Foo")
            .setDescription("Bar")
            .build();
        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(project));

        // Execute story
        ProjectSummary summary = projectApplicationService.getProjectDetails(projectId0);

        // Assert
        assertEquals(project.getName(), summary.name());
        assertEquals(project.getDescription(), summary.description());
    }

    // User tries to access details on a non-existent project
    @Test
    public void shouldThrowExceptionWhenAccessingDetailsOnNonExistentProject() {
        // Setup
        when(projectRepository.findById(projectId0)).thenReturn(Optional.empty());

        // Assert
        assertThrows(ProjectNotFoundException.class, 
        () -> projectApplicationService.getProjectDetails(projectId0));
    }

    // User accesses details on an archived project
    @Test
    public void shouldReturnProjectSummaryOfArchivedProject() {
        // Setup
        ProjectBuilder archivedBuilder = new ArchivedProject.ArchivedProjectBuilder();
        Project project = archivedBuilder.setId(projectId0)
            .setName("Baz")
            .setDescription("Bar")
            .build();
        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(project));

        // Execute story
        ProjectSummary summary = projectApplicationService.getProjectDetails(projectId0);

        // Assert
        assertEquals(project.getName(), summary.name());
        assertEquals(project.getDescription(), summary.description());
    }

    // User accesses all active projects
    @Test
    public void shouldGetAllActiveProjects() {
        // Setup
        Project project0 = projectBuilder.setName("Foo")
            .setDescription("Bar")
            .build();
        Project project1 = projectBuilder.setName("Baz")
            .setDescription("Qux")
            .build();
        List<Project> projects = new ArrayList<>();
        projects.add(project0);
        projects.add(project1);
        when(projectRepository.findActiveProjects()).thenReturn(projects);

        // Execute story
        List<ProjectSummary> activeProjects = projectApplicationService.listActiveProjects();

        // Assert
        for (int i = 0; i < activeProjects.size(); ++i) {
            Project project = projects.get(i);
            ProjectSummary summary = activeProjects.get(i);
            assertEquals(project.getName(), summary.name());
            assertEquals(project.getDescription(), summary.description());
        }
    }

    // User accesses all archived projects
    @Test
    public void shouldGetAllArchivedProjects() {
        // Setup
        ProjectBuilder archiveBuilder = new ArchivedProject.ArchivedProjectBuilder();
        Project project0 = archiveBuilder.setName("Foo")
            .setDescription("Bar")
            .build();
        Project project1 = archiveBuilder.setName("Baz")
            .setDescription("Qux")
            .build();
        List<Project> projects = new ArrayList<>();
        projects.add(project0);
        projects.add(project1);
        when(projectRepository.findArchivedProjects()).thenReturn(projects);

        // Execute story
        List<ProjectSummary> archivedProjects = projectApplicationService.listArchivedProjects();

        // Assert
        for (int i = 0; i < archivedProjects.size(); ++i) {
            Project project = projects.get(i);
            ProjectSummary summary = archivedProjects.get(i);
            assertEquals(project.getName(), summary.name());
            assertEquals(project.getDescription(), summary.description());
        }
    }

    // User queries whether a project has overdue tasks (Positive)
    @Test
    public void shouldReturnTrueWhenOverdueTask() {
        // Setup
        Instant dueDate = Instant.EPOCH;
        Instant currentDate = dueDate.plus(1, ChronoUnit.DAYS);
        Clock clock = Clock.fixed(currentDate, ZoneId.of(TestConstant.FIXED_ZONE_ID));
        Task overdueTask = taskBuilder.setStatus(TaskStatus.IN_PROGRESS)
            .setDueDate(Optional.of(dueDate))
            .build();
        Project project = projectBuilder.setId(projectId0)
            .addTask(overdueTask)
            .build();
        Project projectSpy = spy(project);
        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(projectSpy));

        // Execute story
        Boolean hasOverdueTasks = projectApplicationService.hasOverdueTasks(
            projectId0, 
            clock
        );

        // Assert
        assertTrue(hasOverdueTasks);
        verify(projectSpy).hasOverdueTasks(clock);
    }

    // User queries whether a project has overdue tasks (Negative)
    @Test
    public void shouldFalseTrueWhenNoOverdueTask() {
        // Setup
        Instant dueDate = Instant.EPOCH;
        Instant currentDate = dueDate.minus(1, ChronoUnit.DAYS);
        Clock clock = Clock.fixed(currentDate, ZoneId.of(TestConstant.FIXED_ZONE_ID));
        Task overdueTask = taskBuilder.setStatus(TaskStatus.IN_PROGRESS)
            .setDueDate(Optional.of(dueDate))
            .build();
        Project project = projectBuilder.setId(projectId0)
            .addTask(overdueTask)
            .build();
        Project projectSpy = spy(project);
        when(projectRepository.findById(projectId0)).thenReturn(Optional.of(projectSpy));

        // Execute story
        Boolean hasOverdueTasks = projectApplicationService.hasOverdueTasks(
            projectId0, 
            clock
        );

        // Assert
        assertFalse(hasOverdueTasks);
        verify(projectSpy).hasOverdueTasks(clock);
    }

    // User queries whether a non-existent project has overdue tasks

    /*
     * I am unsure of the remaining test cases. The success of the story is tied to
     * how the projectRepository behaves. Will it return an empty list to 
     * ProjectApplicationService? Am I rather testing how the application service
     * handles empty lists?
     */
    // User lists active projects when none exist (should return empty list)

    // User lists archived projects when none exist (should return empty list)


}
