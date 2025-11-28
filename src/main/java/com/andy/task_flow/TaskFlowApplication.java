package com.andy.task_flow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.ArchivedProject.ArchivedProjectBuilder;
import com.andy.task_flow.domain.repositories.ProjectRepository;

@SpringBootApplication
public class TaskFlowApplication {

	private static final Logger logger = LoggerFactory.getLogger(TaskFlowApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TaskFlowApplication.class, args);
	}

	/**
	 * The following method is inspired by: https://spring.io/guides/gs/accessing-data-jpa
	 */
	@Bean
	public CommandLineRunner demo(ProjectRepository repository) {
		ArchivedProjectBuilder archivedProjectBuilder = new ArchivedProjectBuilder();

		return (args) -> {
			logger.info("Type of repository: ");
			logger.info("--------------------------");
			logger.info(repository.getClass().toString());

			logger.info("");

			logger.info("Creating projects");
			logger.info("--------------------------");
			repository.save(ProjectImpl.of("Foo", "Bar"));
			AbstractProject project = ProjectImpl.of("Qux", "Baz");
			AbstractProject archivedProject = archivedProjectBuilder.fromProject(project).build();
			repository.save(archivedProject);

			logger.info("");

			logger.info("Projects found with findAll()");
			logger.info("--------------------------");
			List<UUID> ids = new ArrayList<>();
			repository.findAll().forEach(p -> {
				logger.info(p.toString());
				ids.add(p.getId());
			});

			logger.info("");

			logger.info("Find project by id");
			logger.info("--------------------------");
			AbstractProject project0 = repository.findById(ids.get(0)).get();
			logger.info(project0.toString());

			logger.info("");

			logger.info("Find all ACTIVE projects");
			logger.info("--------------------------");
			repository.findActiveProjects().forEach(p -> {
				logger.info(p.toString());
			});

			logger.info("");

			logger.info("Find all ARCHIVED project");
			logger.info("--------------------------");
			repository.findArchivedProjects().forEach(p -> {
				logger.info(p.toString());
			});

			logger.info("");

			logger.info("Delete a project");
			logger.info("--------------------------");
			repository.delete(project0);
			logger.info("Then call findAll() to see the project gone");
			repository.findAll().forEach(p -> {
				logger.info(p.toString());
			});
		};
	}

}
