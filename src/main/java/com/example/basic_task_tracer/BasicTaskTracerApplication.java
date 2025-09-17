package com.example.basic_task_tracer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching

public class BasicTaskTracerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicTaskTracerApplication.class, args);
	}

}
