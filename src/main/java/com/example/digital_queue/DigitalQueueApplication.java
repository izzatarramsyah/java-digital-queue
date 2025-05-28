package com.example.digital_queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DigitalQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalQueueApplication.class, args);
	}

}
