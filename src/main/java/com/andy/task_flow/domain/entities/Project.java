package com.andy.task_flow.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.UUID;
import java.util.Set;

@Entity
public class Project {
    
    @Id
    private final UUID id = UUID.randomUUID();

    @OneToMany(mappedBy = "project")
    private Set<Task> tasks;

}
