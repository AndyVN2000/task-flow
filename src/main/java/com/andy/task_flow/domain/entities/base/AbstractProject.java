package com.andy.task_flow.domain.entities.base;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.entities.interfaces.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;


@MappedSuperclass
public abstract class AbstractProject implements Project {

    /**
     * andistoev/onion‑architecture‑with‑spring‑boot
     * Andistoev uses a LinkedHashSet, instead of HashSet in this kind of situation.
     * So why would I use LinkedHashSet in my specific case?
     * It comes with the advantage of ordering items based on the order they er added
     * to the set. Does it matter for Projects that contains sets of Tasks?
     * For now, we define tasks as a List, since this is what ChatGPT (our fake customer)
     * specifies.
     * 
     * Update: tasks will be refactored from List<Task> to Map<UUID,Task> because
     *  operations on this collection (e.g. removeTask()) are ID-based and
     *  task are identified by IDs in general.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    protected Map<UUID,Task> tasks = new LinkedHashMap<>();

    @Column
    protected String name;

    @Column
    protected String description;

    @Column
    protected Instant createdAt;
    
    @Override
    public List<Task> getTasks() {
        return List.copyOf(tasks.values());
    }

}
