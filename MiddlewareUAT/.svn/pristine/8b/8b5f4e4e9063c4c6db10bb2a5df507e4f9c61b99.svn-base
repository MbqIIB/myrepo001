package com.npst.middleware.sms.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.npst.middleware.obj.Message;
import com.npst.middleware.sms.service.GupShupSmsServive;
import com.npst.middleware.util.AESEncryptionUtility;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.Util;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Service
public class GupShupSmsServiveImpl implements GupShupSmsServive
{
	private final static Logger LOG = LoggerFactory.getLogger(GupShupSmsServiveImpl.class);
	private final static String NOTIFICATION_QUEUE = Util.getProperty("NOTIFICATION_QUEUE");
	private final static String SMS_QUEUE = Util.getProperty("SMS_QUEUE");
	private final static String OTP_QUEUE = Util.getProperty("OTP_QUEUE");
	private static ConnectionFactory factory = null;
	private final static String host = Util.getProperty("QHOST");
	private final static String user = Util.getProperty("QUSERNAME");
	private final static String pass = AESEncryptionUtility.decrypt(Util.getProperty("QPASSWORD"), AESEncryptionUtility.secretKeys);

	@Autowired
	TaskExecutor taskExecutor;

	@Override
	public void sendMessage(Message message)
	{
		LOG.info("Inside sendMessage with message for Mob {} and message {} ", message.getMobileNo(), message.getMessage());
		try
		{
			if (factory == null)
			{
				factory = new ConnectionFactory();
			}
			LOG.trace("Going to set RMQ connection:");
			factory.setHost(host);
			//factory.setPort(5671);
			factory.setUsername(user);
			factory.setPassword(pass);
			//factory.useSslProtocol();
			//LOG.info("Getting all info for set RMQ connection with host {} , user {} and pass {} ", host,user ,pass);
			taskExecutor.execute(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						LOG.info("going for new connection using connectionFactory at ",new Date());
						Connection connection = factory.newConnection();
						LOG.info("Connection using connectionFactory obtained at ",new Date());
						Channel channel = connection.createChannel();
						/*if (ConstantNew.MESSAGE_TYPE_FCM.equalsIgnoreCase(message.getType()))
						{
							channel.basicPublish("", NOTIFICATION_QUEUE, null, Util.getJSONStr(message).getBytes());
						}
						if (ConstantNew.MESSAGE_TYPE_SMS.equalsIgnoreCase(message.getType()))
						{
							channel.basicPublish("", SMS_QUEUE, null, Util.getJSONStr(message).getBytes());
						}
						if (ConstantNew.MESSAGE_TYPE_OTP.equalsIgnoreCase(message.getType()))
						{
							channel.basicPublish("", OTP_QUEUE, null, Util.getJSONStr(message).getBytes());
						}*/
						if ("YES".equals(Util.getProperty("PROD_NOTI"))) {
							if (ConstantNew.MESSAGE_TYPE_FCM.equalsIgnoreCase(message.getType())) {
								channel.basicPublish("", NOTIFICATION_QUEUE, null,
										Util.convertObjectIntoByteArray(message));
								LOG.info("publish message in NOTIFICATION_QUEUE:");
							}
							if (ConstantNew.MESSAGE_TYPE_SMS.equalsIgnoreCase(message.getType())) {
								channel.basicPublish("", SMS_QUEUE, null, Util.convertObjectIntoByteArray(message));
								LOG.info("publish message in SMS_QUEUE:");
							}
							if (ConstantNew.MESSAGE_TYPE_OTP.equalsIgnoreCase(message.getType())) {
								channel.basicPublish("", OTP_QUEUE, null, Util.convertObjectIntoByteArray(message));
								LOG.info("publish message in OTP_QUEUE:");
							}
						} else {
							if (ConstantNew.MESSAGE_TYPE_FCM.equalsIgnoreCase(message.getType())) {
								channel.basicPublish("", NOTIFICATION_QUEUE, null, Util.getJSONStr(message).getBytes());
							}
							if (ConstantNew.MESSAGE_TYPE_SMS.equalsIgnoreCase(message.getType())) {
								channel.basicPublish("", SMS_QUEUE, null, Util.getJSONStr(message).getBytes());
							}
							if (ConstantNew.MESSAGE_TYPE_OTP.equalsIgnoreCase(message.getType())) {
								channel.basicPublish("", OTP_QUEUE, null, Util.getJSONStr(message).getBytes());
							}
						}
						channel.close();
						connection.close();
					}
					catch (Exception e)
					{
						LOG.error(e.getMessage(), e);
					}
				}
			});
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
	}
}
