package com.andy.task_flow.domain.entities.interfaces;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.Task;

public interface Project {
    
    UUID getId();

    String getName();

    String getDescription();

    Instant getCreatedAt();

    Optional<Instant> getArchivedAt();

    Optional<String> getArchivedBy();

    /**
     * External mutation on the list of tasks should not be possible.
     * @return an immutable list of tasks.
     */
    List<Task> getTasks();

    boolean isArchived();

    int getCompletedTaskCount();

    // TODO: this method is weird. Must think about how to deal with it
    boolean hasOverdueTasks(Clock clock);

}
