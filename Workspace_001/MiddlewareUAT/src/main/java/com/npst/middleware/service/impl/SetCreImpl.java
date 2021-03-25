package com.npst.middleware.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.dao.ErrorCodeRepository;
import com.npst.middleware.obj.Message;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.service.SetCre;
import com.npst.middleware.sms.service.GupShupSmsServive;
import com.npst.middleware.sms.service.SmsProcess;
import com.npst.middleware.upipin.service.UpiPinProcess;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.Util;

@Service
public class SetCreImpl implements SetCre
{
	private static final Logger LOG = LoggerFactory.getLogger(SetCreImpl.class);
	@Autowired
	public UpiPinProcess upiPinProcess;
	@Autowired
	public ErrorCodeRepository errorCodeRepository;
	
	@Autowired
	GupShupSmsServive			gupShupSmsServive;
	
	@Autowired
	SmsProcess					smsProcess;

	@Override
	public ReqResp perform(final ReqResp reqResp)
	{
		LOG.trace(" ");
		try
		{
			String mobileNo = reqResp.getRegMobile();
			String accNo = reqResp.getPayerAcNum();
			String newUpiPin = reqResp.getCredNMpin();
			String upiPin = reqResp.getCredMpin();
			String upiRespCode = upiPinProcess.changeUpiPin(upiPin, newUpiPin, mobileNo, accNo);
			if (ConstantNew.SUCCESS_CODE.equals(upiRespCode))
			{
				reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
				String smsMessage=Util.getSMSProperty("SMS_C170");//Circuler RMD 170
				//smsProcess.sendSms(mobileNo, smsMessage);
				if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION")))
				{
					Message message = new Message();
					message.setMobileNo(mobileNo);
					message.setType(ConstantNew.MESSAGE_TYPE_SMS);
					message.setMessage(smsMessage);
					this.gupShupSmsServive.sendMessage(message);
				}
				else
				{
					smsProcess.sendSms(mobileNo, smsMessage);
				}
			}
			else
			{
				reqResp.setRespCode(upiRespCode);
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		LOG.info("Response code of change upi pin is {}" ,reqResp.getRespCode());
		return reqResp;
	}
}
