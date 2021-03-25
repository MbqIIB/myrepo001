package com.npst.mobileservice.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQSend {
	static ConnectionFactory factory = null;
	static Logger log = Logger.getLogger(RabbitMQSend.class);
	static String QIP = Util.getProperty("QIP");
	static String QUSER = Util.getProperty("QUSER");
	static String QPASS = AESEncryptionUtility.decrypt(Util.getProperty("QPASS"), AESEncryptionUtility.secretKeys);

	public static void main(String[] args) {
		send("a", "MOBILE.TO.PSP");
	}

	public static void send(String message, String QUEUE_NAME) {
		// String QUEUE_NAME = Util.getProperty("EMAILQUEUE");
		try {

			if (null == factory) {
				log.debug("Creating new ConnectionFactory for RabbitMQ");
				factory = new ConnectionFactory();
			}
			factory.setHost(QIP);
			factory.setUsername(QUSER);
			factory.setPassword(QPASS);
			//factory.useSslProtocol();
			//factory.setPort(5671);
			Connection connection;
			connection = factory.newConnection();
			Channel channel = connection.createChannel();
			// channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			log.info(" [x] Sent To Queue for Process");
			channel.close();
			connection.close();
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		}
	}
}
