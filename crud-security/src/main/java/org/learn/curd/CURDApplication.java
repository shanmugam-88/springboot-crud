package org.learn.curd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaAuditing(auditorAwareRef="auditorAware")
public class CURDApplication {

	public static void main(String[] args) {
		SpringApplication.run(CURDApplication.class, args);
	}

}
