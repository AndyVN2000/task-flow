package com.andy.task_flow.domain.repositories;

import com.andy.task_flow.domain.entities.Task;

import java.util.UUID;
import java.util.Optional;

public interface TaskRepository {
    
    // Accounts for both creating a new task or updating existing task (id already exists)
    Task save(Task task);

    Optional<Task> findById(UUID id);

    void delete(UUID id);

}
