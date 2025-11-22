package com.andy.task_flow.domain.repositories;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.base.AbstractTask;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    
    // Accounts for both creating a new task or updating existing task (id already exists)
    AbstractTask save(AbstractTask task);

    Optional<AbstractTask> findById(UUID id);

    List<AbstractTask> findByProject(AbstractProject project);

    List<AbstractTask> findAll();

    void delete(UUID id);

}
