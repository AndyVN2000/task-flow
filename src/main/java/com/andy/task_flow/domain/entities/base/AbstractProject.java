package com.andy.task_flow.domain.entities.base;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.andy.task_flow.domain.entities.Task;
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
     * TODO: Ask our 'customer' for user stories on what end user can do with a collection
     *       of tasks in a project.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    protected List<Task> tasks = new ArrayList<>();

    @Column
    protected String name;

    @Column
    protected String description;

    @Column
    protected Instant createdAt;
    
    @Override
    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

}
