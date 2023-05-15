package com.remidiousE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan("com.remidiousE")
public class ELibraryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELibraryAppApplication.class, args);
	}

}