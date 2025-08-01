package com.andy.task_flow.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.UUID;
import java.util.HashSet;
import java.util.Set;
import java.time.Instant;

@Entity
public class Project {
    
    @Id
    private final UUID id = UUID.randomUUID();

    /**
     * andistoev/onion‑architecture‑with‑spring‑boot
     * Andistoev uses a LinkedHashSet, instead of HashSet in this kind of situation.
     * So why would I use LinkedHashSet in my specific case?
     * It comes with the advantage of ordering items based on the order they er added
     * to the set. Does it matter for Projects that contains sets of Tasks?
     */
    @OneToMany(mappedBy = "project")
    private Set<Task> tasks = new HashSet<>();


    private String name;

    private String description;

    private Instant createdAt;

    public Project of(String name, String description) {
        return new Project(name, description);
    }

    private Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
