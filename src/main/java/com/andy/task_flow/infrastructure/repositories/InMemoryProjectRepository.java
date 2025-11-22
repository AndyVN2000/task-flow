package com.andy.task_flow.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.repositories.ProjectRepository;

public class InMemoryProjectRepository implements ProjectRepository {

    @Override
    public Project save(Project project) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Optional<Project> findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Project> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<Project> findActiveProjects() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findActiveProjects'");
    }

    @Override
    public List<Project> findArchivedProjects() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findArchivedProjects'");
    }

    @Override
    public void delete(Project project) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
