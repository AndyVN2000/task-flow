Project Domain Specification

---

1. Core Concept
- A Project represents a collection of tasks grouped under a business goal.
- States:
  - Active (mutable) -> ProjectImpl
  - Archived (immutable) -> ArchivedProject
- Both implement the Project interface.

---

2. Fields (Domain Data)
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Unique identifier for the project. |
| name | String | Human-readable name. |
| description | String | Optional description. |
| tasks | List<Task> | Collection of tasks in the project. |
| createdAt | Instant | Timestamp of project creation. |
| archivedAt | Optional<Instant> | Timestamp when archived (empty if active). |
| archivedBy | Optional<String> | User who archived the project (empty if active). |

---

3. Domain Operations

On all Projects (Project interface):
- UUID getId()
- String getName()
- String getDescription()
- List<Task> getTasks() (unmodifiable copy)
- Instant getCreatedAt()
- Optional<Instant> getArchivedAt()
- Optional<String> getArchivedBy()
- boolean isArchived()
- int getCompletedTaskCount()
- boolean hasOverdueTasks(Clock clock)

Notes:
- All getters must return consistent, non-null (or Optional) values.
- getTasks() should not allow external mutation.
- When a project is archived, it preserves its unique identifier (id), so archived and active projects share the same identity

On active/mutable Projects (MutableProject interface):
- void rename(String newName)
- void changeDescription(String newDescription)
- void addTask(Task task)
- void removeTask(Task task)
- ArchivedProject archive(String archivedBy, Clock clock)

On archived/immutable Projects (ArchivedProject):
- Implements Project interface.
- Getters work normally.
- Mutating methods must throw UnsupportedOperationException.

---

4. Invariants & Business Rules
- id is immutable.
- createdAt is set at construction and immutable.
- Archiving is irreversible.
- A Task belongs to exactly one Project.
- Once archived, the project state cannot change.
- isArchived() must reflect the true state.
- archivedAt and archivedBy must be consistent with isArchived().

---

5. Repository Operations (ProjectRepository)
- Optional<Project> findById(UUID id)
- List<Project> findAll()
- void save(Project project)
- void delete(Project project)

---

6. Lifecycle Example
1. Create a new ProjectImpl with name and description.
2. Add tasks, modify name/description as needed.
3. Call archive() when finished.
4. System produces an ArchivedProject (immutable snapshot).

