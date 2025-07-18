package com.andy.task_flow.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.Task;
import com.andy.task_flow.domain.entities.Project;

public class TaskTest {
    
    private Task task;

    @BeforeEach
    public void setup() {
        Project project = new Project();
        task = Task.of(project);
    }
    
    @Test
    public void testTaskBelongsToAProject() {
        assertNotNull(task.getProject());
    }

}
