package com.npst.middleware.sms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.npst.middleware.sms.service.SmsProcess;
import com.npst.middleware.util.GupShupSms;

@Service
public class SmsProcessImpl implements SmsProcess
{
	private static final Logger LOG = LoggerFactory.getLogger(SmsProcessImpl.class);
	@Autowired
	TaskExecutor taskExecutor;

	@Override
	public void sendOtp(final String mobileNo, final String otp)
	{
		LOG.info("starting the sendOtp to mob {} ",mobileNo);
		try
		{
			taskExecutor.execute(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						GupShupSms gupShupSms = new GupShupSms();
						gupShupSms.send(mobileNo, otp);
					}
					catch (Exception e)
					{
						LOG.error(e.getMessage(),e);
					}
				}
			});
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
	}

	@Override
	public void sendSms(String mobileNo, String msg)
	{
		LOG.info("starting the sendSms to mob {}  ",mobileNo);
		try
		{
			taskExecutor.execute(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						GupShupSms gupShupSms = new GupShupSms();
						gupShupSms.send(mobileNo, msg);
					}
					catch (Exception e)
					{
						LOG.error(e.getMessage(),e);
					}
				}
			});
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
	}
}
