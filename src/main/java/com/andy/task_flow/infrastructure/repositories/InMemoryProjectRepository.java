package com.andy.task_flow.infrastructure.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.repositories.ProjectRepository;

public class InMemoryProjectRepository implements ProjectRepository {
    private Map<UUID, AbstractProject> projects = new HashMap<>();

    // ProjectRepository methods

    @Override
    public AbstractProject save(AbstractProject project) {
        projects.put(project.getId(), project);
        return project;
    }

    @Override
    public Optional<AbstractProject> findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<AbstractProject> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<AbstractProject> findActiveProjects() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findActiveProjects'");
    }

    @Override
    public List<AbstractProject> findArchivedProjects() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findArchivedProjects'");
    }

    @Override
    public void delete(AbstractProject project) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    // CrudRepository methods

    @Override
    public long count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void deleteAll(Iterable<? extends AbstractProject> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllById'");
    }

    @Override
    public void deleteById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public boolean existsById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public Iterable<AbstractProject> findAllById(Iterable<UUID> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllById'");
    }

    @Override
    public <S extends AbstractProject> Iterable<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }
    
}
