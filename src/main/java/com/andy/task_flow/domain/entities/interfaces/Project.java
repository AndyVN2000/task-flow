package com.andy.task_flow.domain.entities.interfaces;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.enums.TaskStatus;

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

    default boolean hasOverdueTasks(Clock clock) {
        Instant now = Instant.now(clock);
        for(Task task : getTasks()) {
            Optional<Instant> dueDate = task.getDueDate();
            // Bail-out-fast guards
            if(dueDate.isEmpty()) {
                continue;
            }
            if(task.getStatus() == TaskStatus.COMPLETED) {
                continue;
            }

            // Check if it is overdue
            if(now.isAfter(dueDate.get())) {
                return true;
            }
        }

        return false;
    }

}
