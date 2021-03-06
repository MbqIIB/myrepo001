package com.npst.middleware.service.impl;

import java.net.URLDecoder;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.schema.AcctLienAddRequest;
import com.npst.middleware.schema.AcctLienAddRq;
import com.npst.middleware.schema.AcctLienAddRq.LienDtls;
import com.npst.middleware.schema.BodyType;
import com.npst.middleware.schema.ExecuteFinacleScriptCustomDataType;
import com.npst.middleware.schema.ExecuteFinacleScriptInputVOType;
import com.npst.middleware.schema.ExecuteFinacleScriptRequestType;
import com.npst.middleware.schema.FIXMLType;
import com.npst.middleware.schema.HeaderType;
import com.npst.middleware.schema.MessageKeyType;
import com.npst.middleware.schema.PasswordTokenType;
import com.npst.middleware.schema.RequestHeaderType;
import com.npst.middleware.schema.RequestMessageInfoType;
import com.npst.middleware.schema.SecurityType;
import com.npst.middleware.schema.TokenType;
import com.npst.middleware.service.Soap2RequestService;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.SoapXML2;
import com.npst.middleware.util.Util;
import java.net.URLDecoder;

import scala.annotation.elidable;

@Service
public class Soap2RequestServiceImpl implements Soap2RequestService
{
	private static final Logger LOG = LoggerFactory.getLogger(Soap2RequestServiceImpl.class);

	@Override
	public String getParseFixmlReq(final ReqResp reqResp, final String mobileNo)
	{

		String tempXml = null;
		String responseFromCBS = null;

		StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><SOAP-ENV:Header/><SOAP-ENV:Body><executeService xmlns=\"http://webservice.fiusb.ci.infosys.com\"><arg_0_0><![CDATA[");
		String txnId = "Req_UPI_" + reqResp.getRrn();
		MessageKeyType messageKeyType = new MessageKeyType(txnId, "executeFinacleScript", "10.2", "COR", "");
		RequestMessageInfoType requestMessageInfoType = new RequestMessageInfoType("01", "", "", "", "", Util.getTS());
		PasswordTokenType passwordTokenType = new PasswordTokenType("", "");
		TokenType tokenType = new TokenType(passwordTokenType);
		SecurityType securityType = new SecurityType(tokenType, "", "", "", "", "");
		RequestHeaderType requestHeaderType = new RequestHeaderType(messageKeyType, requestMessageInfoType, securityType);
		HeaderType headerType = new HeaderType(requestHeaderType);
		ExecuteFinacleScriptInputVOType eFSIVO = new ExecuteFinacleScriptInputVOType("GetCifIdFrMobno.scr");
		ExecuteFinacleScriptCustomDataType eFSCDValue = new ExecuteFinacleScriptCustomDataType(mobileNo.substring(2, mobileNo.length()), null);
		ExecuteFinacleScriptRequestType eFSReqValue = new ExecuteFinacleScriptRequestType(eFSIVO, eFSCDValue);
		BodyType bodyType = new BodyType(eFSReqValue);
		bodyType.setExecuteFinacleScriptRequest(eFSReqValue);
		FIXMLType reqFixml = new FIXMLType();
		reqFixml.setBody(bodyType);
		FIXMLType type = new FIXMLType(headerType, bodyType);
		try
		{
			tempXml = Util.marshal(type).toString();
		}
		catch (Exception e1)
		{
			LOG.error(e1.getMessage(),e1);
		}
		String finalTempXml = tempXml.replaceAll("<FIXML xmlns=\"http://www.finacle.com/fixml\">", "<FIXML xsi:schemaLocation=\"http://www.finacle.com/fixml executeFinacleScript.xsd\" xmlns=\"http://www.finacle.com/fixml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
		buffer.append(finalTempXml);
		buffer.append("]]></arg_0_0></executeService></SOAP-ENV:Body></SOAP-ENV:Envelope>");
		String finalXml = buffer.toString();
		LOG.info("Final generated XML is as {} ",finalXml);
		try
		{
			String soapResp = SoapXML2.sendSoapRequest(finalXml);
			if (null != soapResp)
			{
				String tempResponseFromCBS2 = soapResp.replaceAll("&quot;", "\"");
				// String tempResponseFromCBS2="<FIXML
				// xsi:schemaLocation=\"http://www.finacle.com/fixml
				// executeFinacleScript.xsd\"
				// xmlns=\"http://www.finacle.com/fixml\"
				// xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Header><ResponseHeader><RequestMessageKey><RequestUUID>Req_1506324170225</RequestUUID><ServiceRequestId>executeFinacleScript</ServiceRequestId><ServiceRequestVersion>10.2</ServiceRequestVersion><ChannelId>COR</ChannelId></RequestMessageKey><ResponseMessageInfo><BankId>01</BankId><TimeZone></TimeZone><MessageDateTime>2017-09-25T07:19:38.857</MessageDateTime></ResponseMessageInfo><UBUSTransaction><Id/><Status/></UBUSTransaction><HostTransaction><Id/><Status>SUCCESS</Status></HostTransaction><HostParentTransaction><Id/><Status/></HostParentTransaction><CustomInfo/></ResponseHeader></Header><Body><executeFinacleScriptResponse><ExecuteFinacleScriptOutputVO></ExecuteFinacleScriptOutputVO><executeFinacleScript_CustomData><SuccessOrFailure>S</SuccessOrFailure><CustDet><CIF_ID>0902616</CIF_ID><CUST_NAME>KEDARI
				// VINAYAK
				// FAKIRAPPA</CUST_NAME></CustDet></executeFinacleScript_CustomData></executeFinacleScriptResponse></Body></FIXML>";
				responseFromCBS = tempResponseFromCBS2.substring(tempResponseFromCBS2.indexOf("<FIXML"), tempResponseFromCBS2.indexOf("</FIXML>") + "</FIXML>".length());
			}
			else
			{
				LOG.info("NO RESPONSE FROM CBS MEANS NO LIST ACCOUNT FOUND===>>>>",reqResp.getRespCode());
				responseFromCBS = ErrorCode.CBS_R_XY.getUpiCode();
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		LOG.info("Return Successfully from CBS===>>>>" ,responseFromCBS);
		return responseFromCBS;
	}
	
	//Add Lien Amount UPI2.0 API
	
	
	@Override
	public String getParseFixmlReqForLienAdd(ReqResp reqResp, String accNo) {
		String tempXml = null;
		String apiName= null;
		String responseFromCBS = null;
		String startD= null;
		String endD = null;
		BodyType bodyType = null;
		String finalTempXml = null;
		String amount = reqResp.getPayerAmount();
		LOG.debug("Request for Lien add {}",reqResp);
		if (ConstantNew.MANDATE_BLOCK.equalsIgnoreCase(reqResp.getSubOperation())||ConstantNew.MANDTAE_MODIFY.equalsIgnoreCase(reqResp.getSubOperation())){
			if (ConstantNew.MANDTAE_MODIFY.equalsIgnoreCase(reqResp.getSubOperation())){
				startD= reqResp.getValidityStart();
				LOG.debug("Start date in mandated modifyyyyy  {}",startD);
			}
           if (ConstantNew.MANDATE_BLOCK.equalsIgnoreCase(reqResp.getSubOperation())){
	              startD= Util.geDateTFormat(reqResp.getValidityStart());
	              LOG.debug("Start date in mandated blockkkkkk  {}",startD);
			}	
			endD = Util.geDateTFormat(reqResp.getValidityEnd());
			LOG.debug("End date {}",endD);
	      }
		
		StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><SOAP-ENV:Header/><SOAP-ENV:Body><executeService xmlns=\"http://webservice.fiusb.ci.infosys.com\"><arg_0_0><![CDATA[");
		String txnId = "Req_UPI_" + reqResp.getRrn();
		if (ConstantNew.MANDATE_BLOCK.equalsIgnoreCase(reqResp.getSubOperation())){
			apiName="AcctLienAdd";
	      }else {
	    	apiName="AcctLienMod";
		}
		LOG.info("start date in soap class {} ", startD);
		LOG.debug("Mandate Suboperation {}",reqResp.getSubOperation());
		LOG.debug("Value of IsLien{}",reqResp.getIsLien());
		MessageKeyType messageKeyType = new MessageKeyType(txnId, apiName, "10.2", "COR", "");
		RequestMessageInfoType requestMessageInfoType = new RequestMessageInfoType("", "", "", "", "", Util.getTS());
		PasswordTokenType passwordTokenType = new PasswordTokenType("", "");
		TokenType tokenType = new TokenType(passwordTokenType);
		SecurityType securityType = new SecurityType(tokenType, "", "", "", "", "");
		RequestHeaderType requestHeaderType = new RequestHeaderType(messageKeyType, requestMessageInfoType, securityType);
		HeaderType headerType = new HeaderType(requestHeaderType);
		LOG.debug("is this new lean {}",reqResp.getIsLien());
		if (ConstantNew.MANDATE_BLOCK.equalsIgnoreCase(reqResp.getSubOperation()) && "0".equalsIgnoreCase(reqResp.getIsLien())){
			LOG.debug("For lean request block");
			AcctLienAddRq.AcctId acaddRq = new AcctLienAddRq.AcctId();
			acaddRq.setAcctId(accNo);
			AcctLienAddRq.LienDtls.LienDt ld = new AcctLienAddRq.LienDtls.LienDt();
			ld.setEndDt(endD);
			ld.setStartDt(startD);
			AcctLienAddRq.LienDtls.NewLienAmt nlmt = new AcctLienAddRq.LienDtls.NewLienAmt();
			nlmt.setAmountValue(amount);
			nlmt.setCurrencyCode("INR");
			AcctLienAddRq.LienDtls ldet = new AcctLienAddRq.LienDtls(nlmt, ld, "001");
			AcctLienAddRq rq = new AcctLienAddRq(acaddRq,"ULIEN",ldet);
			AcctLienAddRequest accLienAddReq = new  AcctLienAddRequest(rq);
			bodyType = new BodyType(accLienAddReq);
			bodyType.setAcctLienAddRequest(accLienAddReq);
			
			FIXMLType reqFixml = new FIXMLType();
			reqFixml.setBody(bodyType);
			FIXMLType type = new FIXMLType(headerType, bodyType);
			try
			{
				tempXml = Util.marshal(type).toString();
			}
			catch (Exception e1)
			{
				LOG.error(e1.getMessage(),e1);
			}
			
			LOG.debug("temp generated XML is as {} ",tempXml);
			finalTempXml = tempXml.replaceAll("<FIXML xmlns=\"http://www.finacle.com/fixml\">", "<FIXML xsi:schemaLocation=\"http://www.finacle.com/fixml AcctLienAdd.xsd\" xmlns=\"http://www.finacle.com/fixml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
	      }else if (ConstantNew.MANDATE_BLOCK.equalsIgnoreCase(reqResp.getSubOperation()) && "1".equalsIgnoreCase(reqResp.getIsLien())) {
	    	  LOG.debug("For modify request block for Add on existing lean ", startD);
	    	  try {
	    		  LOG.info("cbs in soap class start date is {} ", reqResp.getValidityStart());
	    		  LOG.info("req start date is {} ", startD);
	    		  finalTempXml = MessageFormat.format(URLDecoder.decode( Util.getProperty("UPDATE_XML"), "UTF-8" ),txnId,Util.getTS(),accNo,reqResp.getCbsMandateNo(),reqResp.getUpdateAmount(),reqResp.getValidityStart(),endD);
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		}
		else if (ConstantNew.MANDTAE_MODIFY.equalsIgnoreCase(reqResp.getSubOperation())) {
	    	  LOG.debug("For modify request block with request:{}",reqResp);
	    	  try {
	    		  LOG.info("start date before finalTempXml in update xml {} ", startD);
	    		  finalTempXml = MessageFormat.format(URLDecoder.decode( Util.getProperty("UPDATE_XML"), "UTF-8" ),txnId,Util.getTS(),accNo,reqResp.getCbsMandateNo(),reqResp.getUpdateAmount(),startD,endD);
LOG.info("start date after finalTempXml in update xml {} ", startD);
	    	  } catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		}else{
			LOG.debug("For remove request block");
			try {
				finalTempXml = MessageFormat.format(URLDecoder.decode(Util.getProperty("REVOKE_XML"), "UTF-8" ),txnId,Util.getTS(),accNo,reqResp.getCbsMandateNo(),reqResp.getUpdateAmount());
				LOG.info("start date after finalTempXml in REVOKE_XML xml {} ", startD);
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		}
				
		buffer.append(finalTempXml);
		buffer.append("]]></arg_0_0></executeService></SOAP-ENV:Body></SOAP-ENV:Envelope>");
		String finalXml = buffer.toString();
		LOG.debug("Final generated XML is as {} ",finalXml);
		
		try {
			LOG.debug("Going to send lien amount request to CBS ", startD);
			String soapResp = SoapXML2.sendSoapRequest(finalXml);
			LOG.debug("After getting  lien amount request to CBS {}",soapResp);
			if (null != soapResp) {
				String tempResponseFromCBS2 = soapResp.replaceAll("&quot;", "\"");
				responseFromCBS = tempResponseFromCBS2.substring(tempResponseFromCBS2.indexOf("<FIXML"), tempResponseFromCBS2.indexOf("</FIXML>") + "</FIXML>".length());
			}
			else
			{
				LOG.debug("NO RESPONSE FROM CBS ===>>>>",reqResp.getRespCode());
				responseFromCBS = ErrorCode.CBS_R_XY.getUpiCode();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		LOG.debug("Return Successfully from CBS===>>>>" ,responseFromCBS);
		return responseFromCBS;
	}



	@Override
	public String getParseFixmlReqForLienModify(ReqResp reqResp, String accNo) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getParseFixmlReqForLienRevok(ReqResp reqResp, String accNo) {
		String tempXml = null;
		String responseFromCBS = null;
		StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><SOAP-ENV:Header/><SOAP-ENV:Body><executeService xmlns=\"http://webservice.fiusb.ci.infosys.com\"><arg_0_0><![CDATA[");
		String txnId = "Req_UPI_" + reqResp.getRrn();
		LOG.debug("Lien id for remove lien is {}",reqResp.getCbsMandateNo());
		String finalTempXml = null;
		try {
			  finalTempXml = MessageFormat.format(URLDecoder.decode(Util.getProperty("REVOKE_XML"), "UTF-8" ),txnId,Util.getTS(),accNo,reqResp.getCbsMandateNo(),reqResp.getUpdateAmount());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		
		LOG.debug("Lien remove API is {} ",finalTempXml);
		buffer.append(finalTempXml);
		buffer.append("]]></arg_0_0></executeService></SOAP-ENV:Body></SOAP-ENV:Envelope>");
		String finalXml = buffer.toString();
		LOG.debug("Final generated XML is as {} ",finalXml);
		
		try {
			LOG.debug("Going to send lien lean amount request to CBS");
			String soapResp = SoapXML2.sendSoapRequest(finalXml);
			LOG.debug("After getting  lien remove amount response to CBS {}",soapResp);
			if (null != soapResp) {
				String tempResponseFromCBS2 = soapResp.replaceAll("&quot;", "\"");
				responseFromCBS = tempResponseFromCBS2.substring(tempResponseFromCBS2.indexOf("<FIXML"), tempResponseFromCBS2.indexOf("</FIXML>") + "</FIXML>".length());
			}
			else
			{
				LOG.debug("NO RESPONSE FROM CBS ===>>>>",reqResp.getRespCode());
				responseFromCBS = ErrorCode.CBS_R_XY.getUpiCode();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		LOG.debug("Return Successfully from CBS===>>>>" ,responseFromCBS);
		return responseFromCBS;
	}
	
	
	@Override
	public String getParseFixmlReqForLienInq(ReqResp reqResp, String accNo) {
		String tempXml = null;
		String responseFromCBS = null;
		StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><SOAP-ENV:Header/><SOAP-ENV:Body><executeService xmlns=\"http://webservice.fiusb.ci.infosys.com\"><arg_0_0><![CDATA[");
		String txnId = "Req_UPI_" + reqResp.getRrn();
		LOG.debug("Lien id for Inq lien is {}",reqResp.getCbsMandateNo());
		String finalTempXml = null;
		try {
			  finalTempXml = MessageFormat.format(URLDecoder.decode(Util.getProperty("LIEN_INQ"), "UTF-8" ),txnId,Util.getTS(),accNo);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		
		LOG.debug("Lien Inq API is {} ",finalTempXml);
		buffer.append(finalTempXml);
		buffer.append("]]></arg_0_0></executeService></SOAP-ENV:Body></SOAP-ENV:Envelope>");
		String finalXml = buffer.toString();
		LOG.debug("Final generated XML is as {} ",finalXml);
		
		try {
			LOG.debug("Going to send lien Inq request to CBS");
			String soapResp = SoapXML2.sendSoapRequest(finalXml);
			LOG.debug("After getting  lien Inq amount  to CBS {}",soapResp);
			if (null != soapResp) {
				String tempResponseFromCBS2 = soapResp.replaceAll("&quot;", "\"");
				responseFromCBS = tempResponseFromCBS2.substring(tempResponseFromCBS2.indexOf("<FIXML"), tempResponseFromCBS2.indexOf("</FIXML>") + "</FIXML>".length());
			}
			else
			{
				LOG.debug("NO RESPONSE FROM CBS ===>>>>",reqResp.getRespCode());
				responseFromCBS = ErrorCode.CBS_R_XY.getUpiCode();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		LOG.debug("Return Successfully from CBS===>>>>" ,responseFromCBS);
		return responseFromCBS;
	}
}
