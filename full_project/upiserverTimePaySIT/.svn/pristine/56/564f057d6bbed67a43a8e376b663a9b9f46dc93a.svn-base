package com.npst.upiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.npst.upiserver.constant.ConstantI;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(ConstantI.CONST_JPA_REPO_PATH)
@EntityScan(ConstantI.CONST_ENTITY_PATH)
public class UpiserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(UpiserverApplication.class, args);
	}
}
