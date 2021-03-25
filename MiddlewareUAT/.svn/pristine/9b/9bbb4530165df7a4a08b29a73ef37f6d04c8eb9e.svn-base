package com.npst.middleware.service.impl;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.ci.fiusb.object.FIXMLLienInqResp;
import com.infosys.ci.fiusb.object.FIXMLLienModResp;
import com.infosys.ci.fiusb.object.FIXMLResp;
import com.npst.middleware.obj.Message;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.service.Debit;
import com.npst.middleware.service.LienAmountRequestService;
import com.npst.middleware.service.Reversal;
import com.npst.middleware.service.Soap2RequestService;
import com.npst.middleware.sms.service.GupShupSmsServive;
import com.npst.middleware.sms.service.SmsProcess;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.Util;

@Service
public class LienAmountRequestServiceImpl implements LienAmountRequestService {
	private static final Logger LOG = LoggerFactory.getLogger(LienAmountRequestServiceImpl.class);

	@Autowired
	public Soap2RequestService soap2RequestService;
	@Autowired
	GupShupSmsServive gupShupSmsServive;

	@Autowired
	SmsProcess smsProcess;
	@Autowired
	public Debit debit;
	
	@Autowired
	public Reversal reversal;

	@Override
	public ReqResp perform(ReqResp reqResp) {
		
		LOG.trace(" ");
		LOG.debug("Request Received from UPI Server {}",reqResp);
		try {
			String smsMessage = null;
			String mobileNo = null;
			String accNo = reqResp.getPayerAcNum();
			FIXMLResp respFixml=null;
			FIXMLLienModResp respModFixml = null;
			FIXMLLienInqResp respInqFixml = null;
			String responseInqLienFromCBS = null;
			String cbsStartDate2 = null;
			//Mandate Debit
			if(ConstantNew.MANDTAE_DEBIT.equalsIgnoreCase(reqResp.getSubOperation())) {
				LOG.debug("Going to revoke lien amount req to cbs before debit");
				responseInqLienFromCBS = soap2RequestService.getParseFixmlReqForLienInq(reqResp,accNo);
				if (null == responseInqLienFromCBS || responseInqLienFromCBS.isEmpty() ) {
					reqResp.setRespCode(ErrorCode.CBS_R_XY.getUpiCode());
					return reqResp;
				}
				respInqFixml = (FIXMLLienInqResp)Util.unmarshal(responseInqLienFromCBS, new FIXMLLienInqResp());
				if (null != respInqFixml && "SUCCESS".equalsIgnoreCase(respInqFixml.getHeader().getResponseHeader().getHostTransaction().getStatus())){
					String am = respInqFixml.getBody().getAcctLienInqResponse().getAcctLienInqRs().getLienDtlsRec().getNewLienAmt().getAmountValue();
					LOG.debug("Inquery amount is {}",am);
					Double finalAmount = (Double.parseDouble(am)- Double.parseDouble(reqResp.getPayerAmount()));
					LOG.debug("final  amount is in decimal {} and in string {}",finalAmount,finalAmount.toString());
					reqResp.setUpdateAmount(finalAmount.toString());
				}
				if(respInqFixml.getBody().getError()!=null) {
					reqResp.setRespCode(ErrorCode.CBS_IR.getUpiCode());
					return reqResp;
					 
			   }else {
				   cbsStartDate2=respInqFixml.getBody().getAcctLienInqResponse().getAcctLienInqRs().getLienDtlsRec().getLienDt().getStartDt().toString();
				   LOG.info("else block for Mandate Date issue {} ", cbsStartDate2, reqResp.getValidityStart());
		 		   reqResp.setValidityStart(cbsStartDate2);
			    }
				
				LOG.debug("Validatt Date for Mandate Debit is {}",reqResp.getValidityStart());
				String responseFromCBS = soap2RequestService.getParseFixmlReqForLienRevok(reqResp,accNo);
				if (null == responseFromCBS || responseFromCBS.isEmpty()) {
					reqResp.setRespCode(ConstantNew.ERROR_CODE_NPCI_96);
					return reqResp;
				}
				respModFixml = (FIXMLLienModResp) Util.unmarshal(responseFromCBS, new FIXMLLienModResp());
				if("SUCCESS".equalsIgnoreCase(respModFixml.getHeader().getResponseHeader().getHostTransaction().getStatus())) {
				LOG.debug("Got success remove lean");
				    reqResp = debit.perform(reqResp);
					reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
					reqResp.setUnblockStatus(ConstantNew.UNBLOCK_STATUS_SUCC);
				}
				else {
					LOG.debug("Mandate response from cbs {}","00"+respModFixml.getBody().getError().getFIBusinessException().getErrorDetail().getErrorCode());
					if("005".equals("00"+respModFixml.getBody().getError().getFIBusinessException().getErrorDetail().getErrorCode())){
					reqResp.setRespCode(ConstantNew.MANDATE_HAS_BEEN_REVOKED);
					reqResp.setUnblockStatus(ConstantNew.UNBLOCK_STATUS_FAIL);
					}else {
						reqResp.setRespCode(ConstantNew.NO_RESP);
						return reqResp;
					}
				}
				
				LOG.info("MANDATE Debit Req  TXNID {} AND SUB Opration {} :",reqResp.getTxnId(),reqResp.getSubOperation());
			}else if (ConstantNew.MANDTAE_REV.equalsIgnoreCase(reqResp.getSubOperation())) {
				reqResp = reversal.perform(reqResp);
				LOG.info("MANDATE Debit Reversal  TXNID {} AND SUB Opration {} :",reqResp.getTxnId(),reqResp.getSubOperation());
			}else {
				LOG.debug("going to initiate lien Inq to cbs {}",new Date());
				String inqAmount = null;
				String leanId = null;
				LOG.debug("Going to request getParseFixmlReqForLienInq {}",reqResp);
				responseInqLienFromCBS = soap2RequestService.getParseFixmlReqForLienInq(reqResp,accNo);
				if (null == responseInqLienFromCBS || responseInqLienFromCBS.isEmpty() ) {
					reqResp.setRespCode(ErrorCode.CBS_R_XY.getUpiCode());
					return reqResp;
				}
				respInqFixml = (FIXMLLienInqResp)Util.unmarshal(responseInqLienFromCBS, new FIXMLLienInqResp());
				if(respInqFixml.getBody().getError()!=null) {
					     cbsStartDate2=Util.geDateTFormat(reqResp.getValidityStart());
						 LOG.info("if block for Mandate Date issue{} ", cbsStartDate2, reqResp.getValidityStart());
						 reqResp.setValidityStart(cbsStartDate2); 
				}else {
					   cbsStartDate2=respInqFixml.getBody().getAcctLienInqResponse().getAcctLienInqRs().getLienDtlsRec().getLienDt().getStartDt().toString();
					   LOG.info("else block for Mandate Date issue {} ", cbsStartDate2, reqResp.getValidityStart());
					   reqResp.setValidityStart(cbsStartDate2);
				}
				
				LOG.debug("Validity Date for Mandate Update is {}",reqResp.getValidityStart());
				LOG.debug("going to validate success or fail from lien inq {} ",respInqFixml);
				if ("SUCCESS".equalsIgnoreCase(respInqFixml.getHeader().getResponseHeader().getHostTransaction().getStatus()) || "FAILURE".equalsIgnoreCase(respInqFixml.getHeader().getResponseHeader().getHostTransaction().getStatus()))
				{
					if ("SUCCESS".equalsIgnoreCase(respInqFixml.getHeader().getResponseHeader().getHostTransaction().getStatus())) {
						inqAmount = respInqFixml.getBody().getAcctLienInqResponse().getAcctLienInqRs().getLienDtlsRec().getNewLienAmt().getAmountValue();
						leanId = respInqFixml.getBody().getAcctLienInqResponse().getAcctLienInqRs().getLienDtlsRec().getLienId();
						LOG.debug("Inquery amount is {} and lien Id {}",inqAmount,leanId);
					}else {
						LOG.debug("Inside failure inq block {}","00"+respInqFixml.getBody().getError().getFIBusinessException().getErrorDetail().getErrorCode());
						if ("005".equalsIgnoreCase("00"+respInqFixml.getBody().getError().getFIBusinessException().getErrorDetail().getErrorCode())) {
							inqAmount = "0";
						}
					}
					Double finalAmount= 0.0;
					if ("0".equalsIgnoreCase(inqAmount)) {
						LOG.debug("Inside new Lien with 005 case:{}",inqAmount);
						reqResp.setIsLien("0"); //0 means no lean then need to leanAdd API 1 for 
						reqResp.setUpdateAmount(reqResp.getPayerAmount());
					}else {
						String preAmount = reqResp.getPreviousAmount();
						reqResp.setIsLien("1");
						if (ConstantNew.MANDTAE_MODIFY.equalsIgnoreCase(reqResp.getSubOperation())) {
						finalAmount= (Double.parseDouble(inqAmount)-Double.parseDouble(preAmount))+Double.parseDouble(reqResp.getPayerAmount());  //(inqAmount-previousamount)+current amount
						LOG.info("response from cbs in enquiry in modify block:: date is {} ", cbsStartDate2);
						}else if(ConstantNew.MANDATE_BLOCK.equalsIgnoreCase(reqResp.getSubOperation())){
							finalAmount = (Double.parseDouble(inqAmount)+Double.parseDouble(reqResp.getPayerAmount()));
						}else {
							if(!StringUtils.isEmpty(cbsStartDate2)) {
								reqResp.setValidityStart(cbsStartDate2);
							}else {
								reqResp.setValidityStart(reqResp.getValidityStart());
							}
							finalAmount = (Double.parseDouble(inqAmount)- Double.parseDouble(reqResp.getPayerAmount()));
						}
						LOG.info("inside modify blockstart {} ", cbsStartDate2);
						reqResp.setUpdateAmount(finalAmount.toString());
						reqResp.setCbsMandateNo(leanId);
					}
				}
				LOG.info("reqResp after modify {} ", reqResp.toString());
				LOG.info("cbs start date from response {} ", reqResp.getValidityStart());
				LOG.debug("Before going soap request for lien/Modify/Revoke amont on {} with reqResp as {} and accNo as {} " , new Date(),reqResp ,accNo);
				String responseFromCBS = soap2RequestService.getParseFixmlReqForLienAdd(reqResp,accNo);
				LOG.debug("Response got at {} with value as : {} ", new Date(), responseFromCBS);
				if (null == responseFromCBS || responseFromCBS.isEmpty() ) {
					reqResp.setRespCode(ErrorCode.CBS_R_XY.getUpiCode());
					return reqResp;
				}
				if (ConstantNew.MANDATE_BLOCK.equalsIgnoreCase(reqResp.getSubOperation()) && "0".equalsIgnoreCase(reqResp.getIsLien())) {
					respFixml = (FIXMLResp) Util.unmarshal(responseFromCBS, new FIXMLResp());
				}
				else {
					respModFixml = (FIXMLLienModResp) Util.unmarshal(responseFromCBS, new FIXMLLienModResp());
				}
				if ("SUCCESS".equalsIgnoreCase(respFixml==null?"":respFixml.getHeader().getResponseHeader().getHostTransaction().getStatus()) || "SUCCESS".equalsIgnoreCase(respModFixml.getHeader().getResponseHeader().getHostTransaction().getStatus())) 
				{
					LOG.debug("Inside lien success block for add/remove/modify");
					reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
					String cbsmandateNo = null;
					if (ConstantNew.MANDATE_BLOCK.equalsIgnoreCase(reqResp.getSubOperation())&& "0".equalsIgnoreCase(reqResp.getIsLien())) {
						cbsmandateNo = respFixml.getBody().getAcctLienAddResponse().getAcctLienAddRs().getLienIdRec().getLienId();
					}else {
						LOG.debug("Inside Modify/Revoke response");
						cbsmandateNo = respModFixml.getBody().getAcctLienModResponse().getAcctLienModRs().getLienIdRec().getLienId();
					}
					LOG.debug("LienId is {}" , cbsmandateNo);
					reqResp.setCbsMandateNo(cbsmandateNo);
					//Notification
					if(ConstantNew.MANDATE_BLOCK.equalsIgnoreCase(reqResp.getSubOperation())) {
						smsMessage = MessageFormat.format(Util.getSMSProperty("MANDATE_BLOCK"),ConstantNew.MANDATE_BLOCKED,
								reqResp.getPayerAmount(), Util.maskNumber(reqResp.getPayerAcNum()),reqResp.getRrn());
						mobileNo = reqResp.getPayerDeviceMobile();
					}

					else if(ConstantNew.MANDTAE_UNBLOCK.equalsIgnoreCase(reqResp.getSubOperation())) {
						smsMessage = MessageFormat.format(Util.getSMSProperty("MANDATE_UNBLOCK"),
								ConstantNew.MANDATE_REVOKED,
								reqResp.getPayerAmount(), Util.maskNumber(reqResp.getPayerAcNum()),reqResp.getRrn());
						mobileNo = reqResp.getPayerDeviceMobile();
					}

					else if(ConstantNew.MANDTAE_MODIFY.equalsIgnoreCase(reqResp.getSubOperation())) {
						smsMessage = MessageFormat.format(Util.getSMSProperty("MANDATE_MODIFY"),
								ConstantNew.MANDATE_MODIFYIED,
								reqResp.getPayerAmount(), Util.maskNumber(reqResp.getPayerAcNum()),reqResp.getRrn());
						mobileNo = reqResp.getPayerDeviceMobile();
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
				}else {
					reqResp.setRespCode(ErrorCode.CBS_IR.getUpiCode());
					//reqResp.setRespCode(ConstantNew.ERROR_CODE_NPCI_96);
					return reqResp;
				}
			}//

		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}finally {

		}
		LOG.debug("Lien amount response {}",reqResp.toString());
		return reqResp;
	}

}
