package com.andy.task_flow.fixtures.doubles;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;

public class ProjectStub implements Project {

    @Override
    public UUID getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDescription'");
    }

    @Override
    public Instant getCreatedAt() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCreatedAt'");
    }

    @Override
    public Optional<Instant> getArchivedAt() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getArchivedAt'");
    }

    @Override
    public Optional<String> getArchivedBy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getArchivedBy'");
    }

    @Override
    public List<Task> getTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTasks'");
    }

    @Override
    public boolean isArchived() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isArchived'");
    }

    @Override
    public int getCompletedTaskCount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCompletedTaskCount'");
    }

}
