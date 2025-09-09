Task Domain Specification

---

1. Core Concept
- A Task represents a unit of work inside a Project.
- States: pending, in progress, or completed.
- Each task belongs to exactly one Project.

---

2. Fields (Domain Data)
| Field         | Type              | Description |
|---------------|-------------------|-------------|
| id            | UUID              | Unique identifier for the task. |
| title         | String            | Short human-readable title. |
| description   | String            | Optional longer description. |
| status        | TaskStatus        | Enum: PENDING, IN_PROGRESS, COMPLETED. |
| dueDate       | Optional<Instant> | When the task is due (empty if none). |
| createdAt     | Instant           | When the task was created. |
| completedAt   | Optional<Instant> | When the task was completed (empty if not completed). |
| project       | Project           | The project this task belongs to (immutable association once set). |

---

3. Domain Operations

On all Tasks:
- UUID getId()
- String getTitle()
- String getDescription()
- TaskStatus getStatus()
- Optional<Instant> getDueDate()
- Instant getCreatedAt()
- Optional<Instant> getCompletedAt()
- Project getProject()

On mutable Tasks:
- void rename(String newTitle)
- void changeDescription(String newDescription)
- void setDueDate(Instant dueDate)
- void clearDueDate()
- void start()
- void complete(Clock clock)
- void reopen()

On archived/immutable Tasks:
- Mutator methods must throw UnsupportedOperationException.
- Getters work normally.

---

4. Invariants & Business Rules
- id, createdAt, and project are immutable.
- completedAt must only be set when status == COMPLETED.
- A task cannot be overdue if it is already completed.
- dueDate must not be before createdAt.
- status must always be one of the defined TaskStatus enum values.
- A task must always belong to exactly one Project.

---

5. Repository Operations (TaskRepository)
- Optional<Task> findById(UUID id)
- List<Task> findByProject(Project project)
- List<Task> findAll()
- void save(Task task)
- void delete(Task task)

---

6. Lifecycle Example
1. Create a new Task inside a Project with title, description, and optional due date.
2. Task starts in PENDING.
3. User starts the task -> moves to IN_PROGRESS.
4. User completes the task -> moves to COMPLETED and sets completedAt.
5. If the parent project is archived, the task becomes immutable along with the project.

