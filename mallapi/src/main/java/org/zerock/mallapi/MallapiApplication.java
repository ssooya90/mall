package org.zerock.mallapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class MallapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallapiApplication.class, args);
	}

}
