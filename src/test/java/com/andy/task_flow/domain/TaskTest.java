package com.andy.task_flow.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.andy.task_flow.domain.entities.TaskImpl;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.ProjectImpl;

import java.time.Instant;
import java.util.Optional;

public class TaskTest {
    
    private TaskImpl task;

    @BeforeEach
    public void setup() {
        Project project = ProjectImpl.of("Foo", "Lorem ipsum");
        task = TaskImpl.of("Bar", "dolor sit amet", project, Optional.of(Instant.MAX));
    }

    @Test
    public void taskShouldBelongToProject() {
        assertThrows(NullPointerException.class, () -> 
            TaskImpl.of("Foo", "Baz", null, Optional.of(Instant.MAX)));
    }

    @Test
    public void dueDateShouldBeAnOptionalObject() {
        assertTrue(task.getDueDate() instanceof Optional);
    }
    
    @Test
    @Deprecated
    @Disabled
    public void testTaskBelongsToAProject() {
        assertNotNull(task.getProject());
    }

    

}
