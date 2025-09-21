# task-flow

This project was initialized using the Spring Boot initializer.

## TODO

- Think of user stories based on `project_domain_spec.md` and write unit tests.
  - `[DONE]` Thoroughly test and implement `hasOverdueTasks()` from `Project.java`
  - `[DONE]` Write unit test and implement `ProjectImpl.archive`.
  - `[DONE]` Refactor the collection of tasks in a project from `List<Task>` to `Map<UUID,Task>`.
- `[DONE]` Use the Builder Design Pattern that constructs projects for the purpose of testing.
  - Right now, I have some trouble with getting the builder to add tasks, because of circular reference.
  - Solution: Write test doubles/stub out of `Task` and inject the double/stub into my test cases.

- Completely test and implement implementations of `Task` interface.

## Maven

### Build project

```bash
./mvnw clean install
```

This will also run all test classes.

## Relevant tutorials

### Inspiration from other onion architecture projects

- splaw88/onion‑architecture
  - Multi-module Maven project: `domain`, `application-logic`, `infrastructure`, etc.
  - Two infra options: Spring Boot + H2 + Angular UI, or a console app with in-memory repos
- andistoev/onion‑architecture‑with‑spring‑boot
  - Showcases clear separation: `core`, `infra-api-rest`, `infra-jpa`, etc.
  - Comes with Swagger UI and concrete business rule (“shipping costs”)
Perfect to see how it fits with Spring Web & JPA
- adamsiemion/springboot-with-spring_data-and-onion_architecture
  - Uses in-memory DB (Fongo) for simplicity
  - Includes only one REST endpoint, ideal for quick learning <br>
    Focuses on core principles: domain → application → infrastructure layers

### Many-To-Many

Handling many-to-many relationships using JPA: https://www.baeldung.com/jpa-many-to-many

### Many-To-One

Handling many-to-one relationships using JPA: https://www.bezkoder.com/jpa-manytoone/

It is typically preferable to use the `@ManyToOne` annotation rather than `@OneToMany`.
The reasons are as follows: <br>
With `@OneToMany`, we need to declare a collection (Comments) inside parent class (Tutorial), we cannot limit the size of that collection, for example, in case of pagination. <br>
With `@ManyToOne`, you can modify the Repository:

- to work with Pagination
- or to sort/order by multiple fields

I do not completely understand these reasons. I might need to find other sources.
