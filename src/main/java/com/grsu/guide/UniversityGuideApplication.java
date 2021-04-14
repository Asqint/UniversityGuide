package com.grsu.guide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class UniversityGuideApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityGuideApplication.class, args);
	}

}
