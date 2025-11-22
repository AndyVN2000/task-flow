package com.andy.task_flow.domain.entities;

import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.domain.entities.base.AbstractTask;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Label {

    @Id
    @Column
    private String label;

    @ManyToMany(mappedBy = "labels")
    private Set<AbstractTask> tasks;
    
}
