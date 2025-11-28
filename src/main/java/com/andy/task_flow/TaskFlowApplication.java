package com.andy.task_flow;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.andy.task_flow.domain.entities.ArchivedProject;
import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.ArchivedProject.ArchivedProjectBuilder;
import com.andy.task_flow.domain.entities.interfaces.Project;
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
			repository.findAll().forEach(p -> {
				logger.info(p.toString());
			});
		};
	}

}
