package com.andy.task_flow.domain.entities;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.interfaces.Project;

public class ArchivedProject implements Project {



    public UUID getId() {
        return null;
    }

    public String getName() {
        return "";
    }

    public String getDescription() {
        return "";
    }

    public Instant getCreatedAt() {
        return null;
    }

    public Optional<Instant> getArchivedAt() {
        return null;
    }

    public Optional<String> getArchivedBy() {
        return null;
    }

    public List<Task> getTasks() {
        return null;
    }

    public boolean isArchived() {
        return true;
    }

    public int getCompletedTaskCount() {
        return 0;
    }

    public boolean hasOverdueTasks(Clock clock) {
        return false;
    }
    
}
