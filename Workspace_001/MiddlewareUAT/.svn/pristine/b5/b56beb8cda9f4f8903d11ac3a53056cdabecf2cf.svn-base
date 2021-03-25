package com.npst.middleware.service.impl;

import java.text.MessageFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.cache.CBSErrorCache;
import com.npst.middleware.cbs.service.CBSProcess;
import com.npst.middleware.dao.UpiPinRepository;
import com.npst.middleware.entity.UpiPin;
import com.npst.middleware.obj.Message;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.service.Debit;
import com.npst.middleware.sms.service.GupShupSmsServive;
import com.npst.middleware.sms.service.SmsProcess;
import com.npst.middleware.upipin.service.UpiPinProcess;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.LimitCheck;
import com.npst.middleware.util.PreCheckTrans;
import com.npst.middleware.util.Util;

import scala.annotation.elidable;

@Service
public class DebitImpl implements Debit
{
	private static final Logger LOG = LoggerFactory.getLogger(DebitImpl.class);
	private static final String DEBITPREFIX = "DR";
	@Autowired
	CBSProcess cbsProcess;
	@Autowired
	SmsProcess smsProcess;
	@Autowired
	UpiPinRepository			upiPinRepository;
	@Autowired
	public UpiPinProcess upiPinProcess;
	@Autowired
	LimitCheck limitCheck;
	@Autowired
	GupShupSmsServive gupShupSmsServive;
	
	

	final static long minTxnAmount = Long.valueOf(Util.getProperty("MINTXNAMOUNT")); // 1
	// rs
	// in
	// paisa
	final static long maxTxnAmount = Long.valueOf(Util.getProperty("MAXTXNAMOUNT")); // 10k
	// max
	// txn
	// amount

//	CR - OC82
	private final static String LIMIT200KMCC = Util.getProperty("LIMIT200KMCC");
	private final static String LIMIT200KPURPOSE = Util.getProperty("LIMIT200KPURPOSE");
	private final static String LIMIT200KINITIATION_MODE = Util.getProperty("LIMIT200KINITIATION_MOD");
	
	
	@Override
	public ReqResp perform(final ReqResp reqResp)
	{
		LOG.trace(" ");
		if (PreCheckTrans.check(reqResp))
		{
			LOG.info("Exiting after PreCheckTrans with {} ",reqResp);
			return reqResp;
		}
		try
		{
			if ("MOBILE".equalsIgnoreCase(reqResp.getPayerAddrType()) && "NO".equalsIgnoreCase(Util.getProperty("PAYER_MOBILE_MMID_ALLOW")))
			{
				reqResp.setRespCode(ErrorCode.CBS_R_XJ.getUpiCode());
				return reqResp;
			}
			if ("AADHAAR".equalsIgnoreCase(reqResp.getPayerAddrType()) && "NO".equalsIgnoreCase(Util.getProperty("PAYER_AADHAAR_ALLOW")))
			{
				reqResp.setRespCode(ErrorCode.CBS_R_XJ.getUpiCode());
				return reqResp;
			}

			// Validation limit check
			String payerAmount = Util.convertAmountInPaisa(reqResp.getPayerAmount());
			LOG.info("Amount in paise is as {} ",payerAmount);
			// Checking per txn limit
			
			if (minTxnAmount > Long.parseLong(payerAmount) || maxTxnAmount < Long.parseLong(payerAmount))
			{
				reqResp.setRespCode(ErrorCode.CBS_R_Z8.getUpiCode());
				return reqResp;
			}
			
            if (isEligible200KLimit(reqResp)) {
            	boolean flag[] = limitCheck.isTxnAllowedOnDailyLimitOC82(reqResp.getPayerAcNum(), Long.parseLong(payerAmount)); // 16-11-17
            	if (!flag[0])
    			{
    				reqResp.setRespCode(ErrorCode.CBS_R_Z8.getUpiCode());
    				return reqResp;
    			}
    			if (!flag[1])
    			{
    				reqResp.setRespCode(ErrorCode.CBS_R_Z7.getUpiCode());
    				return reqResp;
    			}
			}else {
				boolean flag[] = limitCheck.isTxnAllowedOnDailyLimit(reqResp.getPayerAcNum(), Long.parseLong(payerAmount)); // 16-11-17
				if (!flag[0])
				{
					reqResp.setRespCode(ErrorCode.CBS_R_Z8.getUpiCode());
					return reqResp;
				}
				if (!flag[1])
				{
					reqResp.setRespCode(ErrorCode.CBS_R_Z7.getUpiCode());
					return reqResp;
				}
			}
			

			// PIN Validation please check and for mandate debit not validate the upipin
			String upiErrorCode = null;
			if(!ConstantNew.MANDTAE_DEBIT.equalsIgnoreCase(reqResp.getSubOperation())){
				upiErrorCode = upiPinProcess.upiPinValidate(reqResp.getCredMpin(), reqResp.getPayerDeviceMobile()// TODO
						// PK
						, reqResp.getPayerAcNum(),reqResp.getPayerAmount()); // 16-11-17
			}else {
				upiErrorCode = ConstantNew.SUCCESS_CODE;
			}
			// reqResp.getPayerAccNum())
			if (ConstantNew.SUCCESS_CODE.equalsIgnoreCase(upiErrorCode))
			{
				cbsProcess.debitAccount(reqResp);
				if (ConstantNew.SUCCESS_CODE.equals(reqResp.getRespCode()))
				{
					try
					{
						UpiPin upiPin=upiPinRepository.findByMobileNoAndAccNoAndStatus(reqResp.getPayerDeviceMobile(),
								reqResp.getPayerAcNum(),ConstantNew.ACTIVE).get(0);
						if(upiPin.getRiskStatus()== null || upiPin.getRiskStatus()==ConstantNew.TXNRISK_STAT_1){
							upiPin.setRiskStatus(ConstantNew.TXNRISK_STAT_2);
							upiPinRepository.save(upiPin);
							
							if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION")))
							{
								Message message = new Message();
								message.setMobileNo(reqResp.getPayerDeviceMobile());
								message.setType(ConstantNew.MESSAGE_TYPE_SMS);
								message.setMessage(Util.getSMSProperty("SMS_C170"));
								this.gupShupSmsServive.sendMessage(message);
							}
							else
							{
								smsProcess.sendSms(reqResp.getPayerDeviceMobile(), Util.getSMSProperty("SMS_C170"));
							}
							
						}else if(upiPin.getRiskStatus()==ConstantNew.TXNRISK_STAT_0){
							upiPin.setFirstTransDate(new Date());
							upiPin.setRiskStatus(ConstantNew.TXNRISK_STAT_1);
							upiPinRepository.save(upiPin);
							if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION")))
							{
								Message message = new Message();
								message.setMobileNo(reqResp.getPayerDeviceMobile());
								message.setType(ConstantNew.MESSAGE_TYPE_SMS);
								message.setMessage(Util.getSMSProperty("SMS_C170"));
								this.gupShupSmsServive.sendMessage(message);
							}
							else
							{
								smsProcess.sendSms(reqResp.getPayerDeviceMobile(), Util.getSMSProperty("SMS_C170"));
							}
						}
						String smsMessage = null;
						String mobileNo = null;
						if ("ACCOUNT".equalsIgnoreCase(reqResp.getPayerAddrType()))
						{ // 16-11-17
							// reqResp.getAddrType()
							String payeeAccountSMS = reqResp.getPayeeAcNum();
							if (payeeAccountSMS == null)
							{
								if (null != reqResp.getPayeeUidNum())
								{
									payeeAccountSMS = reqResp.getPayeeUidNum();
								}
								else
								{
									payeeAccountSMS = reqResp.getPayeeMmid();
								}
							}

							if("AADHAAR".equalsIgnoreCase(reqResp.getPayeeAddrType()))
							{
								smsMessage = MessageFormat.format(Util.getSMSProperty("REM_AADHAAR"), Util.maskNumber(reqResp.getPayerAcNum()), reqResp.getPayerAmount(), new Date(), Util.maskNumber(payeeAccountSMS), reqResp.getRrn());
								//								mobileNo = reqResp.getPayerDeviceMobile();
							}
							else
							{
								smsMessage = MessageFormat.format(Util.getSMSProperty("REM_ACCOUNT"), Util.maskNumber(reqResp.getPayerAcNum()), reqResp.getPayerAmount(), new Date(), // 16-11-17
										// reqResp.getPayerAccNum()
										Util.maskNumber(payeeAccountSMS), reqResp.getRrn()); // 16-11-17
								// reqResp.getPayeeAccNum()
							}
							mobileNo = reqResp.getPayerDeviceMobile();// TODO PK
						}
						if ("MOBILE".equalsIgnoreCase(reqResp.getPayerAddrType()))
						{ // 16-11-17
							// reqResp.getAddrType()
							smsMessage = MessageFormat.format(Util.getSMSProperty("REM_MMID"), Util.maskNumber(reqResp.getPayerAcNum()), // 16-11-17
									// reqResp.getPayerAccNum()
									reqResp.getPayerAmount(), new Date(), reqResp.getPayeeMobileNo(), reqResp.getRrn());
							mobileNo = reqResp.getPayerDeviceMobile();// TODO PK
						}
						if ("AADHAAR".equalsIgnoreCase(reqResp.getPayerAddrType()))
						{ // 16-11-17
							// reqResp.getAddrType()
							smsMessage = MessageFormat.format(Util.getSMSProperty("REM_AADHAAR"), Util.maskNumber(reqResp.getPayerAcNum()), reqResp.getPayerAmount(), new Date(), // 16-11-17
									// reqResp.getPayerAccNum()
									reqResp.getPayeeUidNum(), reqResp.getRrn());
							mobileNo = reqResp.getPayerDeviceMobile();// TODO PK
						}
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
					catch (Exception e)
					{
						LOG.error(e.getMessage(),e);
					}
					reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
				}
				else
				{
					// Getting NPCI Error CODE from CBS Error CODE
					String nPCICODE = CBSErrorCache.debitErrorCode.get(DEBITPREFIX + reqResp.getRespCode());
					if (nPCICODE != null)
					{
						reqResp.setRespCode(nPCICODE);
					}
					else
					{
						reqResp.setRespCode(ConstantNew.INVALID_TXN_XB);
					}
				}
			}
			else
			{
				reqResp.setRespCode(upiErrorCode);
			}

		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}

		LOG.debug("ending the perfom method with {} ",reqResp);
		return reqResp;

	}

	/*
	 * public static void main(String[] args) { GupShupSmsServive
	 * gupShupSmsServive = new GupShupSmsServiveImpl(); SmsProcessImpl
	 * smsProcessImpl=new SmsProcessImpl(); if
	 * (Util.getProperty("ENABLE_NOTIFICATION").equals(ConstantNew.
	 * ENABLE_NOTIFICATION_YES)) { Message message = new Message();
	 * message.setMobileNo("7987612064");
	 * message.setType(ConstantNew.MESSAGE_TYPE_FCM);
	 * message.setMessage("Your Notification send successfully...!!!");
	 * gupShupSmsServive.sendMessage(message); } else {
	 * smsProcessImpl.sendSms("7987612064",
	 * "Your Message send successfully...!!!"); } }
	 */
	private boolean isEligible200KLimit(final ReqResp reqResp) {
		boolean isEligible = false;
//		CR - OC82	
		boolean isMaxLimit200K = false;
		// Check payee mcc code, purpose code or check if the payee is verified merchant
		if (LIMIT200KMCC.contains(reqResp.getPayeeCode())) {
			LOG.info("in side isEligible200KLimit method PAyeecode is {}",reqResp.getPayeeCode());
			isEligible = true;
		} else if (LIMIT200KINITIATION_MODE.equalsIgnoreCase(reqResp.getInitiationMode())) {
			isEligible = true;
		} else if (LIMIT200KPURPOSE.equalsIgnoreCase(reqResp.getTxnPurpose())) {
			isEligible = true;
		}

		return isEligible;
	}

}