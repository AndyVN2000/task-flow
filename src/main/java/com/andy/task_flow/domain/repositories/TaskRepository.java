package com.andy.task_flow.domain.repositories;

import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    
    // Accounts for both creating a new task or updating existing task (id already exists)
    Task save(Task task);

    Optional<Task> findById(UUID id);

    List<Task> findByProject(Project project);

    List<Task> findAll();

    void delete(UUID id);

}
