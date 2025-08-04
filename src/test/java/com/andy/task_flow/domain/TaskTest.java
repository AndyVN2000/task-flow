package com.andy.task_flow.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.andy.task_flow.domain.entities.Task;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.ProjectImpl;

import java.time.LocalDate;
import java.util.Optional;

public class TaskTest {
    
    private Task task;

    @BeforeEach
    public void setup() {
        Project project = ProjectImpl.of("Foo", "Lorem ipsum");
        Task.of("Bar", project, Optional.empty(), LocalDate.MAX);
    }
    
    @Test
    @Deprecated
    @Disabled
    public void testTaskBelongsToAProject() {
        assertNotNull(task.getProject());
    }

    @Test
    public void taskShouldBelongToProject() {
        assertThrows(NullPointerException.class, () -> Task.of("Foo", null, Optional.empty(), LocalDate.MAX));
    }

}
