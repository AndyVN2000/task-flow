package com.andy.task_flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.andy.task_flow.domain.entities.ProjectImpl;
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
		return (args) -> {
			logger.info("Creating projects");
			/* Below line causes a `org.hibernate.StaleObjectStateException`
				Must figure out why this happens */
			// repository.save(ProjectImpl.of("Foo", "Bar"));
		};
	}

}
