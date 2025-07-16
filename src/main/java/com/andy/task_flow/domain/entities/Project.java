package com.andy.task_flow.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Project {
    
    @Id
    private final UUID id = UUID.randomUUID();

}
