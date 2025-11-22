# task-flow

This project was initialized using the Spring Boot initializer.

## Tech stack

- Java
  - Maven
  - junit
  - Mockito
  - JaCoCo

## TODO

### Domain layer

- Think of user stories based on `project_domain_spec.md` and write unit tests.
  - `[DONE]` Thoroughly test and implement `hasOverdueTasks()` from `Project.java`
  - `[DONE]` Write unit test and implement `ProjectImpl.archive`.
  - `[DONE]` Refactor the collection of tasks in a project from `List<Task>` to `Map<UUID,Task>`.
- `[DONE]` Use the Builder Design Pattern that constructs projects for the purpose of testing.
  - Right now, I have some trouble with getting the builder to add tasks, because of circular reference.
  - Solution: Write test doubles/stub out of `Task` and inject the double/stub into my test cases.

- Completely test and implement implementations of `Task` interface.
  - Create a `TaskContractTest.java` that tests on common logic for all subclasses of `AbstractTask`
  - Implement an `ArchivedTask` class that is immutable.
  - Implement the mutator methods of `TaskImpl`.
  - Two tasks are duplicates if they have the same `UUID`. Enforce this business rule.

Development on Domain layer is sufficient. Remaining TODOs are put on hold to spend time on Application layer.

### Application layer

- Get user stories to derive the Application Service classes to write.
- `[DONE]` Refactor the mutator methods of `MutableProject` to be defined in `Project` due to issues in implementing Application layer.  
  Probably do the same with `MutableTask` and `Task`
- `[DONE]` Add Mockito for unit testing Application Layer.
- In `ProjectApplicationServiceTest.java`, remember to add calls on Mockito to `verify`
  interaction between `ProjectApplicationService` and `projectRepository`. The mock object
  is also like a spy that records interactions.

### Infrastructure layer

- For now, start by implementing `InMemory` repository classes.
- Add H2 database for testing.
- If possible integrate real SQL database such as Postgres to project.

## Maven

### Build project

```bash
./mvnw clean install
```

Cleans: deletes `target` folder. Compiles main code. Compiles test code. Runs tests. Builds the `.jar`/`.war`/artifact. Copy the artifact into local Maven repo.

### JaCoCo test report

```bash
./mvnw clean test
```

Only compiles main and test code along with running tests. Practically a subset of operations done with `mvn clean install`.

JaCoCo plugin will generate a report on its code coverage found at `target\site\jacoco\index.html`.

### Running the application

```bash
./mvnw clean spring-boot:run
```

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
  - Includes only one REST endpoint, ideal for quick learning  
    Focuses on core principles: domain → application → infrastructure layers

### Many-To-Many

Handling many-to-many relationships using JPA: [baeldung tutorial on JPA ManyToMany](https://www.baeldung.com/jpa-many-to-many)

### Many-To-One

Handling many-to-one relationships using JPA: [baeldung tutorial on JPA ManyToOne](https://www.bezkoder.com/jpa-manytoone/)

It is typically preferable to use the `@ManyToOne` annotation rather than `@OneToMany`.
The reasons are as follows:  
With `@OneToMany`, we need to declare a collection (Comments) inside parent class (Tutorial), we cannot limit the size of that collection, for example, in case of pagination.  
With `@ManyToOne`, you can modify the Repository:

- to work with Pagination
- or to sort/order by multiple fields

I do not completely understand these reasons. I might need to find other sources.

## Reflection

### Wrong mapped entities

Reached a terrible headache where my entities 'targets' the interface that they were implementing. To be more precise,
`ProjectImpl` and `ArchivedProject` are my concrete entity classes and when I tried to run the application, an error
occurs. The problem was that `Project` (an interface) was targeted by its implementing classes, `ProjectImpl` and `ArchivedProject`. Thusly, Hibernate misreads `Project` as the mapped entity. Rather, Hibernate is supposed to read `AbstractProject` as the mapped entity instead.

To avoid this problem, I had to edit my usage of the JPA decorators accordingly, and even worse rewrite all usages of the interface type `Project` to abstract class type `AbstractProject` in order to stop Hibernate from misreading the wrong mapped entity.

Then to the more interesting part, which was my choices of arguments to the JPA decorators. Regarding to `@Inheritance`, I deemed the `SINGLE_TABLE` strategy as the
most suitable choice apart from `JOINED` and `TABLE_PER_CLASS`, because the only distinction between the two concrete classes `ProjectImpl` and `ArchivedProject` is that one allows writing while the other only allows reading (read more on: [JPA Overview](https://www.geeksforgeeks.org/java/jpa-inheritance-overview/)).

Given my choice of strategy, I also had to use the `@DiscriminatorColumn` decorator to declare on how to distinguish between the entities. I chose to use String as the discriminator type and the column would be named `"isArchived"` where discriminator value for `ProjectImpl` and `ArchivedProject` are set to `FALSE` and `TRUE` respectively. I believe this approach would best convey the meaning behind the hiearchy.
