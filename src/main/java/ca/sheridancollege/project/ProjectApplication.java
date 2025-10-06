package ca.sheridancollege.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot application.
 * Initializes and launches the full web application context.
 */
@SpringBootApplication
public class ProjectApplication {

    /** Starts the application using Spring Boot’s auto-configuration. */
    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }
}
