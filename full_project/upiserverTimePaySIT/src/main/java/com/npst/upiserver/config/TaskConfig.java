package com.npst.upiserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.npst.upiserver.util.MonitorLog;

@Configuration
public class TaskConfig {

	@Value("${TASKEXECUTOR_CORE_POOLSIZE}")
	private int corePoolSize;
	@Value("${TASKEXECUTOR_MAX_POOLSIZE}")
	private int maxPoolSize;
	@Value("${TASKEXECUTOR_QUEUE_CAPACITY}")
	private int queueCapacity;
	@Value("${TASKEXECUTOR_MONITOR_SLEEP}")
	private int sleepMonitor;
	@Value("${TASKEXECUTOR_THREAD_PREFIX}")
	private String taskExecutorThreadPrefix;

	@Value("${TASKSCHEDULER_POOL_SIZE}")
	private int taskSchedulerPoolSize;
	@Value("${TASKSCHEDULER_THREAD_PREFIX}")
	private String taskSchedulerThreadPrefix;

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix(taskExecutorThreadPrefix);
		new Thread(() -> {
			monitor(executor, sleepMonitor);
		}).start();
		return executor;
	}

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(taskSchedulerPoolSize);
		taskScheduler.setThreadNamePrefix(taskSchedulerThreadPrefix);
		return taskScheduler;
	}

	private static void monitor(ThreadPoolTaskExecutor executor, int sleep) {
		while (true) {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
			}
			MonitorLog.statusThreadPool(executor.getActiveCount(),executor.getPoolSize());
		}
	}
}
