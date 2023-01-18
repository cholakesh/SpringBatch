package com.batchprocess.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({ "com.batchprocess.config", "com.batchprocess.service", "com.batchprocess.listener",
		"com.batchprocess.reader", "com.batchprocess.processor", "com.batchprocess.writer",
		"com.batchprocess.controller" })
@EnableAsync
// @EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
