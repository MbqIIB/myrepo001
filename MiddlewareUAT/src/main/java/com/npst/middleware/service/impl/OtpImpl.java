package com.npst.middleware.service.impl;


import com.npst.middleware.service.Otp;
import com.npst.middleware.obj.ReqResp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.npst.middleware.otp.service.OtpProcess;
import com.npst.middleware.sms.service.SmsProcess;
import com.npst.middleware.util.ConstantNew;

@Service
public class OtpImpl implements Otp
{
	private static final Logger LOG = LoggerFactory.getLogger(OtpImpl.class);

	@Autowired
	public OtpProcess otpProcess;

	@Autowired
	SmsProcess smsProcess;

	@Override
	public ReqResp send(ReqResp reqResp)
	{
		LOG.trace(" ");
		try
		{
			reqResp = otpProcess.send(reqResp);
			reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		LOG.info("ending the otp send method with response as {} ",reqResp.getRespCode());
		return reqResp;
	}
}