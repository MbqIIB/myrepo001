package com.npst.middleware.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.ci.fiusb.object.RespFixml;
import com.npst.middleware.cache.CBSErrorCache;
import com.npst.middleware.cbs.service.CBSProcess;
import com.npst.middleware.dao.ErrorCodeRepository;
import com.npst.middleware.obj.Message;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.obj.ReqResp.Account;
import com.npst.middleware.obj.ReqResp.Account.CredsAllowed;
import com.npst.middleware.service.ListAccount;
import com.npst.middleware.service.Soap2RequestService;
import com.npst.middleware.sms.service.GupShupSmsServive;
import com.npst.middleware.sms.service.SmsProcess;
import com.npst.middleware.upipin.service.UpiPinProcess;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.Util;

@Service
public class ListAccountImpl implements ListAccount
{
	private static final Logger LOG = LoggerFactory.getLogger(ListAccountImpl.class);
	@Autowired
	public CBSProcess cbsProcess;
	@Autowired
	public ErrorCodeRepository errorCodeRepository;
	@Autowired
	public UpiPinProcess upiPinProcess;
	@Autowired
	public Soap2RequestService soap2RequestService;
	@Autowired
	GupShupSmsServive			gupShupSmsServive;
	
	@Autowired
	SmsProcess					smsProcess;
	
	
	@Override
	public ReqResp fetch(final ReqResp reqResp)
	{
		LOG.trace(" ");
		String mobileNo = null;
		try
		{
			mobileNo = String.valueOf(reqResp.getLinkValue());
			LOG.debug("Before going soap request  on {} with reqResp as {} and mobileNo as {} " , new Date(),reqResp ,mobileNo);
			String responseFromCBS = soap2RequestService.getParseFixmlReq(reqResp, mobileNo);
			LOG.debug("Response got at {} with value as : {} ", new Date(), responseFromCBS);
			if (null == responseFromCBS || ErrorCode.CBS_XH.getUpiCode().equals(responseFromCBS))
			{
				reqResp.setRespCode(ErrorCode.CBS_XH.getUpiCode());
				return reqResp;
			}
			
			RespFixml respFixml = (RespFixml) Util.unmarshal(responseFromCBS, new RespFixml());
			try
			{
				if ("F".equalsIgnoreCase(respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getSuccessOrFailure()))
				{
					reqResp.setRespCode(ErrorCode.CBS_XH.getUpiCode());
					return reqResp;
				}
			}
			catch (Exception e)
			{
				LOG.error(e.getMessage(), e);
			}
			int size = respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getCustDet().size();
			LOG.info("Fatching customer Id with size {}  " , size);
			if (1 == size)
			{
				if (respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getCustDet() != null)
				{
					String custId = respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getCustDet().get(0).getCIFID();
					LOG.info("fetching customer id from cbs {} ", custId);
					String custName = respFixml.getBody().getExecuteFinacleScriptResponse().getExecuteFinacleScriptCustomData().getCustDet().get(0).getCustName();
					LOG.info("For Customer ID {} and name {} received from CBS ",custId,custName);
					cbsProcess.fatchAccounts(reqResp, custId, custName, mobileNo);
				}
				else
				{
					reqResp.setRespCode(ErrorCode.CBS_XH.getUpiCode());
					LOG.info("Returning with response code : " , ErrorCode.CBS_XH.getUpiCode());
					return reqResp;
				}
			}
			else if (0 == size)
			{
				reqResp.setRespCode(ErrorCode.CBS_XH.getUpiCode());
				LOG.trace("No Record found for mob in CBS");
				return reqResp;
			}
			else if (size > 1)
			{
				reqResp.setRespCode(ErrorCode.CBS_BR.getUpiCode());
				return reqResp;
			}
			if (ConstantNew.SUCCESS_CODE.equals(reqResp.getRespCode()))
			{
				List<Account> accounts = reqResp.getAccounts();
				List<Account> accountsMb = new ArrayList<>();
				for (Account account : accounts)
				{
					boolean flag = upiPinProcess.isUpiPinCreated(reqResp.getLinkValue(), account.getAccRefNumber());
					if (flag)
					{
						account.setMbeba(ConstantNew.CONST_Y);
					}
					else
					{
						account.setMbeba(ConstantNew.CONST_N);
					}
					account.setAeba(ConstantNew.CONST_N);
					List<CredsAllowed> credsAlloweds = new ArrayList<>();
					CredsAllowed c = new CredsAllowed();
					c.setdLength("6");
					c.setdType("NUMERIC");
					c.setSubType("MPIN");
					c.setType("PIN");
					CredsAllowed c2 = new CredsAllowed();
					c2.setdLength("6");
					c2.setdType("NUMERIC");
					c2.setSubType("SMS");
					c2.setType("OTP");
					if ("Y".equalsIgnoreCase(Util.getProperty("FORMAT2")))
					{
						CredsAllowed c1 = new CredsAllowed();
						c1.setdLength("4");
						c1.setdType("NUMERIC");
						c1.setSubType("ATMPIN");
						c1.setType("PIN");
						credsAlloweds.add(c1);
					}
					credsAlloweds.add(c);
					credsAlloweds.add(c2);
					account.setCredsAlloweds(credsAlloweds);
					accountsMb.add(account);
				}
				reqResp.setAccounts(accountsMb);
				reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
				
				
				if (ConstantNew.ENABLE_NOTIFICATION_YES.equals(Util.getProperty("ENABLE_NOTIFICATION"))) {
					LOG.info("Going to set message in dto for listaccount:");
					Message message = new Message();
					message.setMobileNo(mobileNo);
					message.setType(ConstantNew.MESSAGE_TYPE_SMS);
//					String smsMessage=Util.getSMSProperty("LIST_ACCOUNT");
					String smsMessage=MessageFormat.format(Util.getSMSProperty("LIST_ACCOUNT"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
					message.setMessage(smsMessage);
					LOG.info("Set message in dto and ready to send the message gupShup Sms Servive");
					this.gupShupSmsServive.sendMessage(message);
					LOG.info("Send message successfully in rmq server:");
				} else {
					LOG.info("Before going to send message without rmq for listaccount ");
					String smsMessage=MessageFormat.format(Util.getSMSProperty("LIST_ACCOUNT"),Util.getProperty("BANK_CUSTOMER_NUMBER"));
					smsProcess.sendSms(mobileNo, smsMessage);
//					GupShupSms.send(mobileNo, smsMessage);
					LOG.info("Successfully send message from GupShupSms.send:");
				}
			} else {
				if (CBSErrorCache.listAccoutErrorCode.get(reqResp.getRespCode()) != null) {
					reqResp.setRespCode(CBSErrorCache.listAccoutErrorCode.get(reqResp.getRespCode()));
				}
				else
				{
					reqResp.setRespCode(ErrorCode.CBS_XH.getUpiCode());
				}
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		finally
		{
		}
		LOG.info("Ending the fetch method with response as {} ", reqResp);
		return reqResp;
	}
}