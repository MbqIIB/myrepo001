package com.npst.upiserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.npst.upiserver.constant.ConstantI;
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
			factory.setHost(host);
			factory.setUsername(user);
			factory.setPassword(pass);
			Connection connection;
			try {
				connection = factory.newConnection();
				Channel channel = connection.createChannel();
				Map<String, Object> headers = new HashMap<>();
				Date dt = new Date();
				String dateTime = UtilLogger.SDFUNI.format(dt);
				headers.put(ConstantI.CONST_DATE_TIME, ConstantI.CONST_BLANK + dateTime);
				headers.put(ConstantI.CONST_LOG_FILE, ConstantI.CONST_BLANK + logFile);
				channel.basicPublish(ConstantI.CONST_BLANK, queueName,
						new AMQP.BasicProperties.Builder().headers(headers).build(), logMsg.getBytes());
				log.info(" [x] Sent Log to Queue for Process [", logFile, "]");
				channel.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("error :{}", e);
			}

		}
	}

	private static final Logger log = LoggerFactory.getLogger(UtilLogger.class.getName());

	final static SimpleDateFormat SDFUNI = new SimpleDateFormat(ConstantI.CONST_DATE_TIME_FORMAT);
	private static ConnectionFactory factory = null;
	static String host = Util.getProperty(ConstantI.CONST_QHOST);
	static String user = Util.getProperty(ConstantI.CONST_QUSERNAME);
	static String pass = AESEncryptionUtility.decrypt(Util.getProperty(ConstantI.CONST_QPASSWORD),
			AESEncryptionUtility.secretKeys);
	static String queueName = Util.getProperty(ConstantI.CONST_LOGQUEUE_NAME);
    private static boolean isEnable="YES".equalsIgnoreCase(Util.getProperty("IS_LOGWRITER_ENABLE"));
	public static void writeTextFile(String logMsg, String logFile) {
		if(isEnable) {
		LoggerThreadPool.getExecutor().execute(new UtilLogThread(logMsg, logFile));
		}
	}
}
