package com.scheduler.curi;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CuriApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuriApplication.class, args);
	}

}
