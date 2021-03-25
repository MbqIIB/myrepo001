package com.npst.upiserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorLog {
	private static final Logger log = LoggerFactory.getLogger(ErrorLog.class);

	public static <T> void sendError(Exception e, T className) {
		log.error("ClassName={} ,Cause={} ,error {}", className, e.getCause(), e);
	}

	public static <T> void sendError(Exception e, String msg, T className) {
		log.error("ClassName={} ,Cause={},Msg={} ,error {}", className, e.getCause(), msg, e);
	}

	public static <T> void sendError(String error, String msg, T className) {
		log.error("ClassName={} ,error={} ,{} ", className, error, msg);
	}

	public static <T> void sendError(String error, String[] msgs, T className) {
		log.error("ClassName={} ,error={} ,{} ", className, error, msgs);
	}
}
