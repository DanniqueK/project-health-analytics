package com.project.health_analytics;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main class for the Health Analytics Spring Boot application.
 * This class is the starting point for the application and handles the
 * application startup event.
 *
 * @author Dannique Klaver
 */
@SpringBootApplication
@RestController
public class HealthAnalyticsApplication implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private Environment environment;

	/**
	 * Main method to run the Spring Boot application.
	 *
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(HealthAnalyticsApplication.class, args);
	}

	/**
	 * Event handler that is called when the application is ready.
	 * This method retrieves the actual server port and prints it to the console.
	 * 
	 * @param event The application ready event.
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		String port = environment.getProperty("local.server.port");
		System.out.println("Application is running on port: " + port);
	}

}