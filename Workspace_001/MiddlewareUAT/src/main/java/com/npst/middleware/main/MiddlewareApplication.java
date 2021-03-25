package com.npst.middleware.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.npst.middleware.util.ConstantNew;

@SpringBootApplication
@SpringBootConfiguration
@EnableJpaRepositories(ConstantNew.ENABLE_JPA_REPO)
@ComponentScan(ConstantNew.COMPONENT_SCAN)
@EntityScan(ConstantNew.ENTITY_SCAN)
public class MiddlewareApplication
{
	private static final Logger LOG = LoggerFactory.getLogger(MiddlewareApplication.class);

	public static void main(String[] args)
	{
		try
		{
			SpringApplication.run(MiddlewareApplication.class, args);
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
	}
}
