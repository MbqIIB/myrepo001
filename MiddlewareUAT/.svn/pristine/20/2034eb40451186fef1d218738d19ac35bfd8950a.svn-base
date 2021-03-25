package com.npst.middleware.service.impl;

import java.text.MessageFormat;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.cache.CBSErrorCache;
import com.npst.middleware.cbs.service.ATMProcess;
import com.npst.middleware.hsm.HSMUtil;
import com.npst.middleware.obj.Message;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.otp.service.OtpProcess;
import com.npst.middleware.service.ReqRegMob;
import com.npst.middleware.sms.service.GupShupSmsServive;
import com.npst.middleware.sms.service.SmsProcess;
import com.npst.middleware.upipin.service.UpiPinProcess;
import com.npst.middleware.util.Constant;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.PinBlockEncryptionUtil;
import com.npst.middleware.util.Util;

@Service
public class ReqRegMobImpl implements ReqRegMob
{
	private static final Logger LOG = LoggerFactory.getLogger(ReqRegMobImpl.class);
	
	final static String HSM_PRODUCTION = Util.getProperty("HSM_PRODUCTION");
	@Autowired
	public OtpProcess otpProcess;
	@Autowired
	public UpiPinProcess upiPinProcess;
	@Autowired
	public ATMProcess			aTMProcess;
	
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
			String regDetailsType = reqResp.getRegDetailsType();
			if (Constant.MOB.equalsIgnoreCase(reqResp.getPayerDeviceType()))
			{
					
				otpProcess.validate(reqResp);
				if (ConstantNew.SUCCESS_CODE.equalsIgnoreCase(reqResp.getRespCode()))
				{
					atmValidation(regDetailsType, reqResp);
					if (ConstantNew.SUCCESS_CODE.equalsIgnoreCase(reqResp.getRespCode()))//reqResp.getRespCode()
					{
						String payerName = null;
						if (null != reqResp.getPayerName())
						{
							payerName = reqResp.getPayerName().trim();
							if (payerName.length() >= 50)
							{
								LOG.info("Payer name if length is greter than 50 char: " ,payerName);
								payerName = payerName.substring(0, 49);
							}

						}
						String response = upiPinProcess.setUpiPin(reqResp.getCredMpin(), reqResp.getRegMobile(), reqResp.getPayerAcNum(), reqResp.getPayerDeviceType(), reqResp.getPayerAddr(), payerName); // TODO
						if (ConstantNew.SUCCESS_CODE.equalsIgnoreCase(response))
						{
							reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
							if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION"))) {
								LOG.info("Going to set message in dto: for upipin ");
								Message message = new Message();
								String mobileNo=reqResp.getRegMobile();
								message.setMobileNo(mobileNo);
								message.setType(ConstantNew.MESSAGE_TYPE_SMS);
//								String smsMessage=Util.getSMSProperty("SET_UPIPIN");
								String smsMessage=MessageFormat.format(Util.getSMSProperty("SET_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
								message.setMessage(smsMessage);
								LOG.info("Set message in dto and ready to send the message gupShup Sms Servive ");
								this.gupShupSmsServive.sendMessage(message);
								smsProcess.sendSms(mobileNo, Util.getSMSProperty("SMS_C170"));
								LOG.info("Send message successfully in rmq server:");
							} else {
								LOG.info("Going to set message in dto: for upipin ");
								String smsMessage=MessageFormat.format(Util.getSMSProperty("SET_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
								String mobileNo=reqResp.getRegMobile();
								smsProcess.sendSms(mobileNo, smsMessage);
								smsProcess.sendSms(mobileNo, Util.getSMSProperty("SMS_C170"));
//								GupShupSms.send(mobileNo, smsMessage);
								LOG.info("Successfully send message from GupShupSms.send:");
							}
						} else {
							reqResp.setRespCode(response);
							if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION"))) {
								LOG.info("Going to set message in dto: for upipin ");
								Message message = new Message();
								String mobileNo=reqResp.getRegMobile();
								message.setMobileNo(mobileNo);
								message.setType(ConstantNew.MESSAGE_TYPE_SMS);
								String smsMessage=MessageFormat.format(Util.getSMSProperty("F_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
								message.setMessage(smsMessage);
								LOG.info("Set message in dto and ready to send the message gupShup Sms Servive ");
								this.gupShupSmsServive.sendMessage(message);
								LOG.info("Send message successfully in rmq server:");
							} else {
								LOG.info("Before going to send message without rmq upipin ");
								String smsMessage=MessageFormat.format(Util.getSMSProperty("F_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
								String mobileNo=reqResp.getRegMobile();
								smsProcess.sendSms(mobileNo, smsMessage);
//								GupShupSms.send(mobileNo, smsMessage);
								LOG.info("Successfully send message from GupShupSms.send:");
							}
						}
					}else{
						if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION"))) {
							LOG.info("Going to set message in dto: for upipin ");
							Message message = new Message();
							String mobileNo=reqResp.getRegMobile();
							message.setMobileNo(mobileNo);
							message.setType(ConstantNew.MESSAGE_TYPE_SMS);
							String smsMessage=MessageFormat.format(Util.getSMSProperty("F_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
							message.setMessage(smsMessage);
							LOG.info("Set message in dto and ready to send the message gupShup Sms Servive ");
							this.gupShupSmsServive.sendMessage(message);
							LOG.info("Send message successfully in rmq server:");
						} else {
							LOG.info("Before going to send message without rmq  ");
							String smsMessage=MessageFormat.format(Util.getSMSProperty("F_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
							String mobileNo=reqResp.getRegMobile();
							smsProcess.sendSms(mobileNo, smsMessage);
//							GupShupSms.send(mobileNo, smsMessage);
							LOG.info("Successfully send message from GupShupSms.send:");
						}
					}
				}else{
					// otp validation faile registration failure message
					if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION"))) {
						LOG.info("Going to set message in dto: for upipin ");
						Message message = new Message();
						String mobileNo=reqResp.getRegMobile();
						message.setMobileNo(mobileNo);
						message.setType(ConstantNew.MESSAGE_TYPE_SMS);
						String smsMessage=MessageFormat.format(Util.getSMSProperty("F_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
						message.setMessage(smsMessage);
						LOG.info("Set message in dto and ready to send the message gupShup Sms Servive ");
						this.gupShupSmsServive.sendMessage(message);
						LOG.info("Send message successfully in rmq server:");
					} else {
						LOG.info("Before going to send message without rmq  ");
						String smsMessage=MessageFormat.format(Util.getSMSProperty("F_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
						String mobileNo=reqResp.getRegMobile();
						smsProcess.sendSms(mobileNo, smsMessage);
//						GupShupSms.send(mobileNo, smsMessage);
						LOG.info("Successfully send message from GupShupSms.send:");
					}
				}
			} else if ("USDC".equalsIgnoreCase(reqResp.getPayerDeviceType()))// TODO
																				// PK
			{
				atmValidation(regDetailsType, reqResp);
				if (ConstantNew.SUCCESS_CODE.equalsIgnoreCase(reqResp.getRespCode())) {
				String response = upiPinProcess.setUpiPin(reqResp.getCredMpin(), reqResp.getRegMobile(), reqResp.getPayerAcNum(), reqResp.getPayerDeviceType(),reqResp.getPayerAddr(), reqResp.getPayerName());
				if (ConstantNew.SUCCESS_CODE.equalsIgnoreCase(response))
				{
					reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
					if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION"))) {
						LOG.info("Going to set message in dto:");
						Message message = new Message();
						String mobileNo=reqResp.getRegMobile();
						message.setMobileNo(mobileNo);
						message.setType(ConstantNew.MESSAGE_TYPE_SMS);
						String smsMessage=MessageFormat.format(Util.getSMSProperty("SET_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
						message.setMessage(smsMessage);
						LOG.info("Set message in dto and ready to send the message gupShup Sms Servive ");
						this.gupShupSmsServive.sendMessage(message);
						smsProcess.sendSms(mobileNo, Util.getSMSProperty("SMS_C170"));
						LOG.info("Send message successfully in rmq server:");
					} else {
						LOG.info("Before going to send message without rmq  ");
						String smsMessage=MessageFormat.format(Util.getSMSProperty("SET_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
						String mobileNo=reqResp.getRegMobile();
						smsProcess.sendSms(mobileNo, smsMessage);
						smsProcess.sendSms(mobileNo, Util.getSMSProperty("SMS_C170"));
//						GupShupSms.send(mobileNo, smsMessage);
						LOG.info("Successfully send message from GupShupSms.send:");
					}
				} else {
					reqResp.setRespCode(response);
					if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION"))) {
						LOG.info("Going to set message in dto: for upipin ");
						Message message = new Message();
						String mobileNo=reqResp.getRegMobile();
						message.setMobileNo(mobileNo);
						message.setType(ConstantNew.MESSAGE_TYPE_SMS);
						String smsMessage=MessageFormat.format(Util.getSMSProperty("F_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
						message.setMessage(smsMessage);
						LOG.info("Set message in dto and ready to send the message gupShup Sms Servive ");
						this.gupShupSmsServive.sendMessage(message);
						LOG.info("Send message successfully in rmq server:");
					} else {
						LOG.info("Before going to send message without rmq  ");
						String smsMessage=MessageFormat.format(Util.getSMSProperty("F_UPIPIN"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
						String mobileNo=reqResp.getRegMobile();
						smsProcess.sendSms(mobileNo, smsMessage);
//						GupShupSms.send(mobileNo, smsMessage);
						LOG.info("Successfully send message from GupShupSms.send:");
					}
				}
			}
			}
		} catch (Exception e) {
			LOG.error("exception caught at perfom method [" + e + "]");
			e.printStackTrace();
		}
		LOG.debug("Ending the perfom method with {} ",reqResp);
		return reqResp;
	}

	public OtpProcess getOtpProcess()
	{
		return otpProcess;
	}

	public void setOtpProcess(OtpProcess otpProcess)
	{
		this.otpProcess = otpProcess;
	}

	public UpiPinProcess getUpiPinProcess()
	{
		return upiPinProcess;
	}

	public void setUpiPinProcess(UpiPinProcess upiPinProcess)
	{
		this.upiPinProcess = upiPinProcess;
	}

	public ATMProcess getaTMProcess()
	{
		return aTMProcess;
	}

	public void setaTMProcess(ATMProcess aTMProcess)
	{
		this.aTMProcess = aTMProcess;
	}

	private void atmValidation(final String formatType, final ReqResp reqResp)
	{
		LOG.info("Before going atmValidation with formatType {} ",formatType);
		aTMProcess.getFullCardNumber(reqResp);
		if (ConstantNew.ATM_SUCCESS_CODE.equalsIgnoreCase(reqResp.getRespCode()))
		{
			if ("FORMAT2".equalsIgnoreCase(formatType))
			{
				String atmpin = reqResp.getCredAtmpin();
				if ("YES".equalsIgnoreCase(HSM_PRODUCTION))
				{
					String sensitiveData = HSMUtil.getSensitiveData(Base64.getDecoder().decode(atmpin), reqResp.getRegCardDigits());
					reqResp.setCredAtmpin(sensitiveData);
				}
				else
				{
					try
					{
						reqResp.setCredAtmpin(PinBlockEncryptionUtil.encryptPinBlock(reqResp.getRegCardDigits(), Util.decrypt(Base64.getDecoder().decode(atmpin))));
					}
					catch (Exception e)
					{
						LOG.error(e.getMessage(),e);
					}
				}
				aTMProcess.verifyATMPIN(reqResp);
				if (ConstantNew.ATM_SUCCESS_CODE.equalsIgnoreCase(reqResp.getRespCode()))
				{
					reqResp.setRespCode(ConstantNew.SUCCESS_CODE);
				}
				else
				{
					reqResp.setRespCode(CBSErrorCache.getATMErrorCode(reqResp.getRespCode()));
				}
			}
			else
			{
				// send success after verification if format1
				reqResp.setRespCode(ConstantNew.SUCCESS_CODE);
			}
		}
		else
		{
			// ATM error code mapping
			reqResp.setRespCode(CBSErrorCache.getATMErrorCode(reqResp.getRespCode()));
			if(reqResp.getRespCode()==null) {
				reqResp.setRespCode("XN");

			}
		}
	}
}