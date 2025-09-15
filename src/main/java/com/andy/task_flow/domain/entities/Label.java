package com.andy.task_flow.domain.entities;

import com.andy.task_flow.domain.entities.interfaces.Task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Label {

    @Column
    private String label;

    @ManyToMany(mappedBy = "labels")
    private Set<Task> tasks;
    
}
