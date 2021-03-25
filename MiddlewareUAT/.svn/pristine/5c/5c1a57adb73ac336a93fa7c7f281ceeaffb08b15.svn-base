package com.npst.middleware.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
import com.npst.middleware.service.AadharService;
import com.npst.middleware.util.ErrorCode;
import com.npst.middleware.util.SoapXML2;
import com.npst.middleware.util.Util;

@Service
public class AadharServiceImpl implements AadharService
{

	private static final Logger LOG = LoggerFactory.getLogger(AadharServiceImpl.class);

	@Override
	public String getAccountDetails(final String rrn, final String aadhaarNo) throws Exception
	{
		LOG.trace(" ");
		String tempXml1 = null;
		String tempXml = null;
		String responseFromCBS = null;
		String txnId = "Req_" + rrn;
		MessageKeyType messageKeyType = new MessageKeyType(txnId, "executeFinacleScript", "10.2", "COR", "");
		RequestMessageInfoType requestMessageInfoType = new RequestMessageInfoType("01", "", "", "", "", Util.getTS());
		PasswordTokenType passwordTokenType = new PasswordTokenType("", "");
		TokenType tokenType = new TokenType(passwordTokenType);
		SecurityType securityType = new SecurityType(tokenType, "", "", "", "", "");
		RequestHeaderType requestHeaderType = new RequestHeaderType(messageKeyType, requestMessageInfoType, securityType);
		HeaderType headerType = new HeaderType(requestHeaderType);
		ExecuteFinacleScriptInputVOType eFSIVO = new ExecuteFinacleScriptInputVOType("GetAcntFrmAdhr.scr");
		ExecuteFinacleScriptCustomDataType eFSCDValue = new ExecuteFinacleScriptCustomDataType(null, aadhaarNo);
		ExecuteFinacleScriptRequestType eFSReqValue = new ExecuteFinacleScriptRequestType(eFSIVO, eFSCDValue);
		BodyType bodyType = new BodyType(eFSReqValue);
		bodyType.setExecuteFinacleScriptRequest(eFSReqValue);
		FIXMLType reqFixml = new FIXMLType();
		reqFixml.setBody(bodyType);
		FIXMLType type = new FIXMLType(headerType, bodyType);
		try
		{
			tempXml = Util.marshal(type).toString();
			LOG.info("Temp XML ===: {} ", tempXml);
			tempXml1 = tempXml.replaceAll("<FIXML xmlns=\"http://www.finacle.com/fixml\">", "<FIXML xsi:schemaLocation=\"http://www.finacle.com/fixml executeFinacleScript.xsd\" xmlns=\"http://www.finacle.com/fixml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
		}
		catch (Exception e1)
		{
			LOG.error(e1.getMessage(),e1);
		}
		try
		{
			StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><SOAP-ENV:Header/><SOAP-ENV:Body><executeService xmlns=\"http://webservice.fiusb.ci.infosys.com\"><arg_0_0><![CDATA[");
			buffer.append(tempXml1);
			buffer.append("]]></arg_0_0></executeService></SOAP-ENV:Body></SOAP-ENV:Envelope>");
			String finalXml = buffer.toString();
			LOG.info("Final XML sending to CBS at {} with {} " ,new Date(), finalXml);
			String soapResp = SoapXML2.sendSoapRequest(finalXml);
			// String soapResp = "<FIXML
			// xsi:schemaLocation=\"http://www.finacle.com/fixml
			// executeFinacleScript.xsd\" xmlns=\"http://www.finacle.com/fixml\"
			// xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Header><ResponseHeader><RequestMessageKey><RequestUUID>Req_1520068363595</RequestUUID><ServiceRequestId>executeFinacleScript</ServiceRequestId><ServiceRequestVersion>10.2</ServiceRequestVersion><ChannelId>COR</ChannelId></RequestMessageKey><ResponseMessageInfo><BankId>01</BankId><TimeZone></TimeZone><MessageDateTime>2018-03-03T09:15:48.313</MessageDateTime></ResponseMessageInfo><UBUSTransaction><Id
			// /><Status /></UBUSTransaction><HostTransaction><Id
			// /><Status>SUCCESS</Status></HostTransaction><HostParentTransaction><Id
			// /><Status /></HostParentTransaction><CustomInfo
			// /></ResponseHeader></Header><Body><executeFinacleScriptResponse><ExecuteFinacleScriptOutputVO></ExecuteFinacleScriptOutputVO><executeFinacleScript_CustomData><AcntList><FORACID>0900501016560</FORACID><CifId>0902616</CifId><AcctName>KEDARI
			// VINAYAK
			// FAKIRAPPA</AcctName></AcntList></executeFinacleScript_CustomData></executeFinacleScriptResponse></Body></FIXML>";
			// String soapResp="<FIXML
			// xsi:schemaLocation=\"http://www.finacle.com/fixml
			// executeFinacleScript.xsd\"\r\n\txmlns=\"http://www.finacle.com/fixml\"
			// xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n\t<Header>\r\n\t\t<ResponseHeader>\r\n\t\t\t<RequestMessageKey>\r\n\t\t\t\t<RequestUUID>Req_1520068723136</RequestUUID>\r\n\t\t\t\t<ServiceRequestId>executeFinacleScript</ServiceRequestId>\r\n\t\t\t\t<ServiceRequestVersion>10.2</ServiceRequestVersion>\r\n\t\t\t\t<ChannelId>COR</ChannelId>\r\n\t\t\t</RequestMessageKey>\r\n\t\t\t<ResponseMessageInfo>\r\n\t\t\t\t<BankId>01</BankId>\r\n\t\t\t\t<TimeZone></TimeZone>\r\n\t\t\t\t<MessageDateTime>2018-03-03T09:19:28.785</MessageDateTime>\r\n\t\t\t</ResponseMessageInfo>\r\n\t\t\t<UBUSTransaction>\r\n\t\t\t\t<Id
			// />\r\n\t\t\t\t<Status
			// />\r\n\t\t\t</UBUSTransaction>\r\n\t\t\t<HostTransaction>\r\n\t\t\t\t<Id
			// />\r\n\t\t\t\t<Status>SUCCESS</Status>\r\n\t\t\t</HostTransaction>\r\n\t\t\t<HostParentTransaction>\r\n\t\t\t\t<Id
			// />\r\n\t\t\t\t<Status
			// />\r\n\t\t\t</HostParentTransaction>\r\n\t\t\t<CustomInfo
			// />\r\n\t\t</ResponseHeader>\r\n\t</Header>\r\n\t<Body>\r\n\t\t<executeFinacleScriptResponse>\r\n\t\t\t<ExecuteFinacleScriptOutputVO>\r\n\t\t\t</ExecuteFinacleScriptOutputVO>\r\n\t\t\t<executeFinacleScript_CustomData>\r\n\t\t\t\t<SuccessOrFailure>F</SuccessOrFailure>\r\n\t\t\t\t<errorMsg>AADHAAR
			// Not Linked to Any
			// Acct</errorMsg>\r\n\t\t\t</executeFinacleScript_CustomData>\r\n\t\t</executeFinacleScriptResponse>\r\n\t</Body>\r\n</FIXML>";
			LOG.info("Response received from CBS===>>> at {} as {}",new Date() ,soapResp);
			if (null != soapResp)
			{
				String tempResponseFromCBS2 = soapResp.replaceAll("&quot;", "\"");
				responseFromCBS = tempResponseFromCBS2.substring(tempResponseFromCBS2.indexOf("<FIXML"), tempResponseFromCBS2.indexOf("</FIXML>") + "</FIXML>".length());
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		LOG.info("Returninf with response {} " ,responseFromCBS);
		return responseFromCBS;
	}
}
