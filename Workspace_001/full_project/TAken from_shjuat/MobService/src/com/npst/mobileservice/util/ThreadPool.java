package com.npst.mobileservice.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * @author Mbyte
 */
public class ThreadPool {
	static Logger log = Logger.getLogger(ThreadPool.class.getName());
	public static ThreadPoolExecutor executor = null;

	// public static ExecutorService executor = null;
	/**
	 *
	 */
	private static Integer POOL_SIZE_MIN = 0;
	private static Integer POOL_SIZE = 0;

	/**
	 * @return
	 */
	public static ExecutorService getThreadFrmTP() {
		log.trace("");
		try {
			if (executor == null) {
				synchronized (ThreadPool.class) {
					if (executor == null) {
						createThreadPool();
					} else {
						return executor;
					}
				}
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		}
		return executor;
	}

	public static boolean getThreadPoolStatus() {
		log.trace("");
		int j = executor.getPoolSize();
		int i = executor.getActiveCount();
		// log(j + " " + i);
		if (i == j - 1 || i == 0) {
			// log("false");
			return false;
		}
		// log("true");
		return true;
	}

	private static void createThreadPool() {
		log.trace("");
		try {
			POOL_SIZE_MIN = Integer.parseInt(Util.getProperty("POOL_SIZE_MIN"));
			if (POOL_SIZE_MIN == 0) {
				POOL_SIZE_MIN = 5;
			}
		} catch (Exception e) {
			POOL_SIZE_MIN = 5;
		}
		try {
			POOL_SIZE = Integer.parseInt(Util.getProperty("POOL_SIZE"));
			if (POOL_SIZE == 0) {
				POOL_SIZE = 5;
			}
		} catch (Exception e) {
			POOL_SIZE = 5;
		}

		try {
			executor = new ThreadPoolExecutor(POOL_SIZE_MIN, POOL_SIZE, 1000, TimeUnit.MILLISECONDS,
					new LinkedBlockingDeque<Runnable>());
			log.info("ThreadPoolExecutor is Created Pool Size " + executor.getCorePoolSize());
			// new MyMonitorThread(executor, 1);
			// new Thread(new MyMonitorThread(executor, 1000)).start();
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			System.exit(0);
		}
	}

	private ThreadPool() {
		super();
	}
}
