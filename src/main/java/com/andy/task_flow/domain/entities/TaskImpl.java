package com.andy.task_flow.domain.entities;

import com.andy.task_flow.domain.enums.*;
import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.domain.entities.interfaces.MutableTask;
import com.andy.task_flow.domain.entities.interfaces.Project;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import java.util.UUID;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;
import java.time.Clock;
import java.time.Instant;

@Entity
@DiscriminatorValue("FALSE")
public class TaskImpl extends AbstractTask implements MutableTask {

    /* 
    @Id
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private final Project project;

    @Column(nullable = false)
    private TaskStatus status = TaskStatus.CREATED;

    @Column(nullable = false)
    private TaskPriority priority;

    @Column(nullable = false)
    private final Instant createdAt = Instant.now();

    @Column
    private Optional<Instant> dueDate;

    @Column
    private Optional<Instant> completedAt;

    // TODO: Study @ManyToMany and @JoinTable on how they work.
    // This tutorial shows how to use the annotations: https://www.baeldung.com/jpa-many-to-many
    @ManyToMany
    @JoinTable(
        name = "task_label",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels = new HashSet<>();
*/    

    private TaskImpl(String title, String description, AbstractProject project, Optional<Instant> dueDate) {
        super(title, description, project, dueDate);
    }
    
    public static TaskImpl of(String title, String description, AbstractProject project, Optional<Instant> dueDate) {
        if (project == null) {
            throw new NullPointerException("Task must belong to a project");
        }
        return new TaskImpl(title, description, project, dueDate);
    }

    // @Override
    // public UUID getId() {
    //     return id;
    // }

    // @Override
    // public String getTitle() {
    //     return title;
    // }

    // @Override
    // public String getDescription() {
    //     return description;
    // }

    // @Override
    // public TaskStatus getStatus() {
    //     return status;
    // }

    // @Override
    // public Optional<Instant> getDueDate() {
    //     return dueDate;
    // }

    // @Override
    // public Instant getCreatedAt() {
    //     return createdAt;
    // }

    // @Override
    // public Optional<Instant> getCompletedAt() {
    //     return Optional.empty();
    // }

    // /**
    //  * It is inappropriate to write a method only for the sake of testing.
    //  * If having this getter is meaningful to the domain or other parts of the system might use this,
    //  *  then it is acceptable to have this method.
    //  * But I only wrote it for the sake of testing, so this is deprecated for now and to be considered
    //  *  non-existent.
    //  * UPDATE: Product owner (ChatGPT) has clarified that getProject() is part of domain. This
    //  *  method is no longer deprecated.
    //  * @return
    //  */
    // @Override
    // public Project getProject() {
    //     return project;
    // }

    // Mutator methods

    @Override
    public void rename(String newName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rename'");
    }

    @Override
    public void changeDescription(String newDescription) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeDescription'");
    }

    @Override
    public void setDueDate(Instant dueDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDueDate'");
    }

    @Override
    public void clearDueDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearDueDate'");
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public void complete(Clock clock) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'complete'");
    }

    @Override
    public void reopen() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reopen'");
    }

}
