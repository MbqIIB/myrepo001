package com.npst.mobileservice.util;

import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class ThreadListener implements ServletContextListener {
	private static final Logger log = Logger.getLogger(ThreadListener.class);
	String hour = "";
	String minute = "";
	String second = "";
	String am_pm = "";
	String stopHour = "";
	String stopMinute = "";
	String stopSecond = "";
	String stopAm_pm = "";
	private ScheduledExecutorService scheduler = null;
	{
		hour = Util.getProperty("schedule_HOUR");
		minute = Util.getProperty("schedule_MINUTE");
		second = Util.getProperty("schedule_SECOND");
		am_pm = Util.getProperty("schedule_AM_PM");
		stopHour = Util.getProperty("stop_HOUR");
		stopMinute = Util.getProperty("stop_MINUTE");
		stopSecond = Util.getProperty("stop_SECOND");
		stopAm_pm = Util.getProperty("stop_AM_PM");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// log.info("Inside contextInitialized method of ThreadListener class for
		// refreshing top bank list");
		// log.info("Refreshing top bank list at " + hour + " :" + minute + " : " +
		// second + " " + am_pm);
		// Calendar calendar = Calendar.getInstance();
		//
		// calendar.set(Calendar.HOUR, Integer.parseInt(hour));
		// calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
		// calendar.set(Calendar.SECOND, Integer.parseInt(second));
		// if (am_pm.equalsIgnoreCase("AM"))
		// calendar.set(Calendar.AM_PM, Calendar.AM);
		// else
		// calendar.set(Calendar.AM_PM, Calendar.PM);
		// Long currentTime = new Date().getTime();
		// if (calendar.getTime().getTime() < currentTime) {
		// calendar.add(Calendar.DATE, 1);
		// }
		// long startScheduler = calendar.getTime().getTime() - currentTime;
		// log.info("closing schedular for Refreshing top bank list at " + stopHour + "
		// :" + stopMinute + " : "
		// + stopSecond + " " + stopAm_pm);
		// calendar.set(Calendar.HOUR, Integer.parseInt(stopHour));
		// calendar.set(Calendar.MINUTE, Integer.parseInt(stopMinute));
		// calendar.set(Calendar.SECOND, Integer.parseInt(stopSecond));
		// if (stopAm_pm.equalsIgnoreCase("AM"))
		// calendar.set(Calendar.AM_PM, Calendar.AM);
		// else
		// calendar.set(Calendar.AM_PM, Calendar.PM);
		// long stopScheduler = calendar.getTime().getTime() - currentTime;
		// log.info(
		// "Refreshing top bank list at " + hour + " :" + minute + " : " + second + " "
		// + am_pm + "got completed");
		// Runnable task = new Runnable() {
		// @Override
		// public void run() {
		// // new MobileService().getTopBanksRefresh(null);
		// }
		// };
		// final ScheduledExecutorService scheduler =
		// Executors.newScheduledThreadPool(1);
		// scheduler.scheduleAtFixedRate(task, startScheduler, stopScheduler,
		// TimeUnit.MILLISECONDS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			scheduler.shutdown();
		} catch (Exception ex) {
		}
	}
}
