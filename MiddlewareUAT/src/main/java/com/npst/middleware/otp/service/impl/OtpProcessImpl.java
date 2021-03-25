package com.npst.middleware.otp.service.impl;

import java.text.MessageFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.core.service.CryptoService;
import com.npst.middleware.dao.OtpRepository;
import com.npst.middleware.entity.OtpRecord;
import com.npst.middleware.hsm.HSMUtil;
import com.npst.middleware.obj.CryptoResp;
import com.npst.middleware.obj.Message;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.otp.service.OtpProcess;
import com.npst.middleware.sms.service.GupShupSmsServive;
import com.npst.middleware.sms.service.SmsProcess;
import com.npst.middleware.util.Constant;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.HashUtil;
import com.npst.middleware.util.Util;

@Service
public class OtpProcessImpl implements OtpProcess
{
	private static final Logger LOG = LoggerFactory.getLogger(OtpProcessImpl.class);
	final static String HSM_PRODUCTION = Util.getProperty("HSM_PRODUCTION");
	final static String OTPMSG = Util.getSMSProperty("OTP_TEXT_FOR_SET_MPIN");
	@Autowired
	public OtpRepository otpRepository;
	@Autowired
	public SmsProcess smsProcess;
	@Autowired
	GupShupSmsServive gupShupSmsServive;
	
	@Autowired
    CryptoService decryptService;

	@Override
	public ReqResp send(ReqResp reqResp)
	{
		LOG.trace(" ");
		String otp = null;
		try
		{
			otp = Util.generateOtp(Constant.otpLen);
			String regMobile = reqResp.getPayerDeviceMobile();
			List<OtpRecord> otpRecords = otpRepository.findByMobileNoAndIsUsed(regMobile, 0);
			for (OtpRecord otpRecord : otpRecords)
			{
				otpRecord.setIsUsed(1);
				otpRecord.setReason("Not Used");
				otpRepository.save(otpRecord);
			}
			OtpRecord otpRecord = new OtpRecord();
			otpRecord.setCreTime(new Date());
			otpRecord.setExpiryTime(DateUtils.addMinutes(new Date(), 15));
			otpRecord.setFailAttempt(0);
			otpRecord.setMaxFailAttempt(ConstantNew.MAX_FAIL_ATTEMPT);
			otpRecord.setMobileNo(reqResp.getPayerDeviceMobile());
			String otpSalt = HashUtil.getSalt();
			String otpHash = HashUtil.crypt(otp, otpSalt);
			otpRecord.setOtp(otpHash);
			otpRecord.setReason(reqResp.getTxnNote());
			otpRecord.setReSendCount(0);
			otpRecord.setWhoReq(reqResp.getPayerAddr());
			otpRecord.setResponse("");
			otpRecord.setIsUsed(0);
			otpRecord.setOtpSalt(otpSalt);
			otpRepository.save(otpRecord);
			if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION")))
			{
				Message message = new Message();
				message.setMobileNo(reqResp.getPayerDeviceMobile());
				message.setType(ConstantNew.MESSAGE_TYPE_SMS);
				//message.setMessage(MessageFormat.format(OTPMSG, otp));
				message.setMessage(MessageFormat.format(OTPMSG,otp,Util.getProperty("BANK_CUSTOMER_NUMBER")));
				this.gupShupSmsServive.sendMessage(message);
			}
			else
			{
				String smsMessage=MessageFormat.format(OTPMSG,otp,Util.getProperty("BANK_CUSTOMER_NUMBER"));
				smsProcess.sendSms(reqResp.getPayerDeviceMobile(),smsMessage);
			}
			reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		return reqResp;
	}

	@Override
	public ReqResp validate(final ReqResp reqResp)
	{
		try
		{
			String regMobile = reqResp.getRegMobile();
			List<OtpRecord> otpRecords = otpRepository.findByMobileNoAndIsUsed(regMobile, 0);
			if (otpRecords == null || otpRecords.size() == 0)
			{
				reqResp.setRespCode(ErrorCode.OTP_ZR.getUpiCode());
			}
			else
			{
				for (OtpRecord otpRecord : otpRecords)
				{
					Date expiryTime = otpRecord.getExpiryTime();
					Date nowTime = new Date();
					if (nowTime.compareTo(expiryTime) > 0)
					{
						reqResp.setRespCode(ErrorCode.OTP_ZS.getUpiCode());
					}
					else
					{
						if (otpRecord.getFailAttempt() >= otpRecord.getMaxFailAttempt())
						{
							reqResp.setRespCode(ErrorCode.OTP_ZT.getUpiCode());
						}
						else
						{
							/*String plainOTP = null;
							if ("YES".equalsIgnoreCase(HSM_PRODUCTION))
							{
								plainOTP = HSMUtil.decryptData(Base64.getDecoder().decode(reqResp.getCredOtp()));

							}
							else
							{
								plainOTP = Util.decrypt(Base64.getDecoder().decode(reqResp.getCredOtp().getBytes()));
							}*/
							String plainOTP = null;
							CryptoResp decryptResp = decryptService.decrypt(reqResp.getCredOtp());
							if (Constant.SUCCESS_STATUS_ZERO.equals(decryptResp.getStatus())) {
								plainOTP = decryptResp.getData();
								decryptResp = null;
							} else {
								reqResp.setRespCode(Constant.HS);
								return reqResp;
							}
							String otpHash = HashUtil.crypt(plainOTP, otpRecord.getOtpSalt());
							if (otpRecord.getMobileNo().equals(regMobile) && otpHash.equals(otpRecord.getOtp()))
							{
								otpRecord.setIsUsed(1);
								reqResp.setRespCode(ConstantNew.SUCCESS_CODE);
							}
							else
							{
								otpRecord.setFailAttempt(otpRecord.getFailAttempt() + 1);
								reqResp.setRespCode(ErrorCode.OTP_ZR.getUpiCode());
							}
						}
					}
					otpRepository.save(otpRecord);
				}
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		LOG.info("Returning from OTPProcessImpl with response as {} ",reqResp);
		return reqResp;
	}
}