package com.npst.upiserver.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerThreadPool {
	private static final Logger log = LoggerFactory.getLogger(LoggerThreadPool.class);

	private static ThreadPoolExecutor executor = null;
	static {
		try {
			executor = new ThreadPoolExecutor(Integer.parseInt(Util.getProperty("LOG_CORE_THREAD_POOL")),
					Integer.parseInt(Util.getProperty("LOG_MAX_THREAD_POOL")), 500, TimeUnit.MILLISECONDS,
					new LinkedBlockingDeque<Runnable>());
			log.info("LoggerThreadPool is created with CorePoolSize={} and maxPoolSize={}", executor.getCorePoolSize(),
					executor.getMaximumPoolSize());

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error creating LoggerThreadPool :{}", e);
			System.exit(0);
		}
	}

	public static ExecutorService getExecutor() {
		return executor;
	}

	public static ThreadPoolExecutor getForStatus() {
		return executor;
	}
}
