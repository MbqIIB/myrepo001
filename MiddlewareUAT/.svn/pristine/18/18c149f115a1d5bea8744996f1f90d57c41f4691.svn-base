package com.npst.middleware.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.npst.middleware.util.Util;

@Configuration
public class Config {
	final static Integer	minPoolSize		= Integer.valueOf(Util.getProperty("MIN_POOL_SIZE"));
	final static Integer	maxPoolSize		= Integer.valueOf(Util.getProperty("MAX_POOL_SIZE"));
	final static Integer	queueCapacity	= Integer.valueOf(Util.getProperty("QUEUE_CAPACITY"));
	
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(minPoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		return executor;
	}
	
}
