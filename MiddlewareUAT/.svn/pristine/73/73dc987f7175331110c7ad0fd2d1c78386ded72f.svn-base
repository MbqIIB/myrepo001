package com.npst.middleware.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.cache.CBSErrorCache;
import com.npst.middleware.cbs.service.CBSProcess;
import com.npst.middleware.obj.Message;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.schema.FIXML;
import com.npst.middleware.schema.FIXML.Body.ExecuteFinacleScriptResponse.ExecuteFinacleScriptCustomData.AcntList;
import com.npst.middleware.service.AadharService;
import com.npst.middleware.service.Credit;
import com.npst.middleware.sms.service.GupShupSmsServive;
import com.npst.middleware.sms.service.SmsProcess;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.ISOMsgConstant;
import com.npst.middleware.util.PreCheckTrans;
import com.npst.middleware.util.Util;

@Service
public class CreditImpl implements Credit
{
	
	private static final Logger LOG = LoggerFactory.getLogger(CreditImpl.class);
	private static final String CREDITPREFIX = "CR";
	private static final String CREDIT_FIR_ALLOW_CONSTANT = Util.getProperty(ConstantNew.CREDIT_FIR_ALLOW_CONSTANT);
	private static final String PAYEE_MOBILE_MMID_ALLOW = Util.getProperty("PAYEE_MOBILE_MMID_ALLOW");
	private static final String PAYEE_AADHAAR_ALLOW  = Util.getProperty("PAYEE_AADHAAR_ALLOW");
	private static final String BEN_ACCOUNT  = Util.getSMSProperty("BEN_ACCOUNT");
	private static final String BEN_MMID = Util.getSMSProperty("BEN_MMID");
	private static final String BEN_AADHAAR = Util.getSMSProperty("BEN_AADHAAR");
	private static final String ENABLE_NOTIFICATION = Util.getProperty("ENABLE_NOTIFICATION");
	
	
	@Autowired
	CBSProcess cbsProcess;

	@Autowired
	SmsProcess smsProcess;
	@Autowired
	GupShupSmsServive gupShupSmsServive;

	@Autowired
	private AadharService aadharServiceImpl;

	@Override
	public ReqResp perform(final ReqResp reqResp)
	{
		LOG.trace(" ");
		if (PreCheckTrans.check(reqResp))
		{
			LOG.debug("Returing after Precheck with {} ", reqResp);
			return reqResp;
		}
		try
		{
			if (ConstantNew.PAYEE_ADDRESS_TYPE_MOBILE.equalsIgnoreCase(reqResp.getPayeeAddrType()) && "NO".equalsIgnoreCase(PAYEE_MOBILE_MMID_ALLOW))
			{
				reqResp.setRespCode(ErrorCode.CBS_B_XK.getUpiCode());
				return reqResp;
			}

			if (ConstantNew.PAYEE_ADDRESS_TYPE_ACCOUNT.equalsIgnoreCase(reqResp.getPayeeAddrType()))
			{
				LOG.debug("before going  Transaction CREDIT Request");
				LOG.debug("is FIR Txn {}" , reqResp.getIsFIR());
				cbsProcess.creditAccount(reqResp);
			}
			if (ConstantNew.PAYEE_ADDRESS_TYPE_AADHAAR.equalsIgnoreCase(reqResp.getPayeeAddrType()))
			{ // 16-11-17
				if (ConstantNew.CONST_N.equalsIgnoreCase(PAYEE_AADHAAR_ALLOW))
				{
					reqResp.setRespCode(ErrorCode.CBS_B_XK.getUpiCode());
					return reqResp;
				}
				fetchAadhaar(reqResp);
				if (ErrorCode.CBS_B_XI.getUpiCode().equals(reqResp.getRespCode()))
				{
					return reqResp;
				}
				cbsProcess.creditAccount(reqResp);
			}
			if (ConstantNew.SUCCESS_CODE.equals(reqResp.getRespCode()))
			{
				try
				{
					String smsMessage = null;
					String mobileNo = null;
					if (ConstantNew.PAYEE_ADDRESS_TYPE_ACCOUNT.equalsIgnoreCase(reqResp.getPayeeAddrType()))
					{ // 16-11-17
						// reqResp.getAddrType()
						smsMessage = MessageFormat.format(BEN_ACCOUNT, Util.maskNumber(reqResp.getPayeeAcNum()), reqResp.getPayeeAmount(), new Date(), Util.maskNumber(reqResp.getPayerAcNum()), reqResp.getRrn()); // 16-11-17
																																																			// reqResp.getPayeeAccNum()
																																																			// ,
																																																			// reqResp.getPayerAccNum()
						mobileNo = reqResp.getPayeeDeviceMobile();// TODO PK
					}
					if (ConstantNew.PAYEE_ADDRESS_TYPE_MOBILE.equalsIgnoreCase(reqResp.getPayeeAddrType()))
					{ // 16-11-17
						// reqResp.getAddrType()
						smsMessage = MessageFormat.format(BEN_MMID, Util.maskNumber(reqResp.getPayeeAcNum()), reqResp.getPayeeAmount(), new Date(), reqResp.getPayerMobileNo(), reqResp.getRrn()); // 16-11-17
																																																			// reqResp.getPayeeAccNum()
						mobileNo = reqResp.getPayeeMobileNo();
					}
					if (ConstantNew.PAYEE_ADDRESS_TYPE_AADHAAR.equalsIgnoreCase(reqResp.getPayeeAddrType()))
					{ // 16-11-17
						// reqResp.getAddrType()
						smsMessage = MessageFormat.format(BEN_AADHAAR, Util.maskNumber(reqResp.getPayeeAcNum()), reqResp.getPayeeAmount(), new Date(), reqResp.getPayerUidNum(), reqResp.getRrn()); // 16-11-17
																																																			// reqResp.getPayeeAccNum()
						mobileNo = reqResp.getPayeeDeviceMobile();// TODO PK
					}
					if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(ENABLE_NOTIFICATION))
					{
						Message message = new Message();
						message.setMobileNo(mobileNo);
						message.setType(ConstantNew.MESSAGE_TYPE_FCM);
						message.setMessage(smsMessage);
						this.gupShupSmsServive.sendMessage(message);
					}
					else
					{
						smsProcess.sendSms(mobileNo, smsMessage);
					}
				}
				catch (Exception ex)
				{
					LOG.error(ex.getMessage(), ex);
				}
				reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
			}

			else
			{
				// Getting NPCI Error CODE from CBS Error CODE
				String nPCICODE = CBSErrorCache.creditErrorCode.get(CREDITPREFIX + reqResp.getRespCode());
				if (nPCICODE != null)
				{
					reqResp.setRespCode(nPCICODE);
				}
				else
				{
//					reqResp.setRespCode(ConstantNew.INVALID_TXN_XC);
					reqResp.setRespCode(ISOMsgConstant.CBS_NO_RESPONSE);
				}
			}

		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
		LOG.info("ending the perform method with {} ", reqResp);
		return reqResp;
	}

	private void fetchAadhaar(final ReqResp reqResp) throws Exception
	{
		LOG.trace(" ");
		String response = aadharServiceImpl.getAccountDetails(reqResp.getRrn(), reqResp.getPayeeUidNum());
		if (response == null)
		{
			reqResp.setRespCode(ErrorCode.CBS_B_XI.getUpiCode());
			return;
		}
		FIXML respFixml = (FIXML) Util.unmarshal(response, new FIXML());
		try
		{
			if ("F".equalsIgnoreCase(respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getSuccessOrFailure()))
			{
				reqResp.setRespCode(ErrorCode.CBS_B_XI.getUpiCode());
				return;
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
			reqResp.setRespCode(ErrorCode.CBS_B_XI.getUpiCode());
			return;
		}
		String accountNumber = null;
		List<AcntList> list = respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getAcntList();
		if (list != null && !list.isEmpty())
		{
			try
			{
				if (respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getAcntList() != null)
				{ // Fetching only first record if more than one account numbers
					// provided by CBS
					accountNumber = respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getAcntList().get(0).getFORACID();
					LOG.info("Account number received from CBS as {} " , accountNumber);
					reqResp.setPayeeAcNum(accountNumber);
					String custName = null;
					String customerId = null;
					try
					{
						custName = respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getAcntList().get(0).getAcctName();
						LOG.info("Customer name received from CBS as {} " , custName);
						customerId = respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getAcntList().get(0).getCifId();
						LOG.info("Customer ID received from CBS as {}" ,customerId);
					}
					catch (Exception e1)
					{
						LOG.error(e1.getMessage(),e1);
					}
				}
				else
				{
					reqResp.setRespCode(ErrorCode.CBS_B_XI.getUpiCode());
					LOG.info("Returning with response code {}",ErrorCode.CBS_B_XI.getUpiCode());
					return;
				}
			}
			catch (Exception e)
			{
				LOG.error(e.getMessage(),e);
				reqResp.setRespCode(ErrorCode.CBS_B_XI.getUpiCode());
			}
		}
		else
		{
			reqResp.setRespCode(ErrorCode.CBS_B_XI.getUpiCode());
			LOG.info("Response code when size is zero is as {}",ErrorCode.CBS_XH.getUpiCode());
		}
	}
}
