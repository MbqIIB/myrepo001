package com.npst.mobileservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Mbyte
 */
public class UtilLogger {
	static class UtilLogThread implements Runnable {
		String logMsg = null, logFile = null;

		public UtilLogThread(String logMsg, String logFile) {
			this.logFile = logFile;
			this.logMsg = logMsg;
		}

		@Override
		public void run() {

			log.trace("logMsg:" + logMsg + "logFile:" + logFile);
			if (factory == null) {
				log.debug("Creating new ConnectionFactory for RabbitMQ");
				factory = new ConnectionFactory();
			}
			factory.setHost(QIP);
			factory.setUsername(QUSER);
			factory.setPassword(QPASS);
			Connection connection;
			try {
				connection = factory.newConnection();
				Channel channel = connection.createChannel();
				Map<String, Object> headers = new HashMap<>();
				Date dt = new Date();
				String dateTime = UtilLogger.SDFUNI.format(dt);
				headers.put("dateTime", "" + dateTime);
				headers.put("logFile", "" + logFile);
				channel.basicPublish("", queueName, new AMQP.BasicProperties.Builder().headers(headers).build(),
						logMsg.getBytes());
				log.info(" [x] Sent Log to Queue for Process " + queueName);
				channel.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e.getStackTrace());
			}

		}
	}

	static Logger log = Logger.getLogger(UtilLogger.class.getName());

	final static SimpleDateFormat SDFUNI = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSSXXX");
	private static ConnectionFactory factory = null;
	static String QIP = Util.getProperty("QIP");
	static String QUSER = Util.getProperty("QUSER");
	static String QPASS = Util.getProperty("QPASS");
	static String queueName = Util.getProperty("LOGQUEUENAME");

	public static void writeTextFile(String logMsg, String logFile) {
		ThreadPool.getThreadFrmTP().execute(new UtilLogThread(logMsg, logFile));
	}
}
