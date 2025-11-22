package com.andy.task_flow.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.TaskImpl;
import com.andy.task_flow.domain.entities.base.AbstractTask;

public abstract class TaskContractTest {

    protected abstract AbstractTask createTask();

    @Test
    public void idShouldBeNonNull() {
        AbstractTask task = createTask();
        assertNotNull(task.getId());
    }

    @Test
    public void dueDateShouldBeAnOptionalObject() {
        AbstractTask task = createTask();
        assertTrue(task.getDueDate() instanceof Optional);
    }

}
