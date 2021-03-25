package com.npst.mobileservice.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.InitiateRequestDao;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.ReqResp;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.service.AuditMobileHomeService;
import com.npst.mobileservice.service.RegistrationHomeService;
import com.npst.mobileservice.service.SecurityEncryptionService;
import com.npst.mobileservice.util.AesEncryption;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.mobileservice.util.UtilLogger;
import com.npst.mobileservice.validator.NegativeMobileNumberValidator;
import com.npst.upi.hor.InitiateRequest;
import com.npst.upi.hor.Registration;

public class AuthFilter implements Filter {
	private static AesEncryption aESEncryptionUtility = null;
	private static com.npst.mobileservice.service.SecurityEncryptionService securityEncryptionService = null;
	private static final String URLMATCH = "Mobilelogin" + "|MobilegetLov" + "|MobilegetMobile" + "|MobileregApp"
			+ "|MobileregGCMId" + "|MobilecheckVpa" + "|MobileerrorLog" + "|Mobiledownloadimage" + "|MobilesendOtp"
			+ "|MobilevalidateOtp" + "|MobilegetNonPrimaryAccVPA" + "|MobileforgetPass" + "|MobilechangePassOutside"
			+ "|MobileaddCrashAnalyticsInfo" + "|MobilegetMaskedAccDet" + "|Mobilefeedback" + "|MobilechangeDeviceIMEI"
			+ "|MobileupdtSilentDeviceIMEI" + "|MobilechangeDeviceWithOtp" + "|Mobileloginwops";

	private class BufferedRequestWrapper extends HttpServletRequestWrapper {

		ByteArrayInputStream bais;
		ByteArrayOutputStream baos;
		BufferedServletInputStream bsis;
		byte[] buffer;

		public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
			super(req);
			InputStream is = req.getInputStream();
			baos = new ByteArrayOutputStream();
			byte buf[] = new byte[1024];
			int letti;
			while ((letti = is.read(buf)) > 0) {
				baos.write(buf, 0, letti);
			}
			buffer = baos.toByteArray();
		}

		public byte[] getBuffer() {
			return buffer;
		}

		@Override
		public ServletInputStream getInputStream() {
			try {
				bais = new ByteArrayInputStream(buffer);
				bsis = new BufferedServletInputStream(bais);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return bsis;
		}
	}

	private class BufferedServletInputStream extends ServletInputStream {
		ByteArrayInputStream bais;

		public BufferedServletInputStream(ByteArrayInputStream bais) {
			this.bais = bais;
		}

		@Override
		public int available() {
			return bais.available();
		}

		@Override
		public int read() {
			return bais.read();
		}

		@Override
		public int read(byte[] buf, int off, int len) {
			return bais.read(buf, off, len);
		}
	}

	private static class ByteArrayPrintWriter {
		private ByteArrayOutputStream baos = new ByteArrayOutputStream();
		private PrintWriter pw = new PrintWriter(baos);
		private ServletOutputStream sos = new ByteArrayServletStream(baos);

		public ServletOutputStream getStream() {
			return sos;
		}

		public PrintWriter getWriter() {
			return pw;
		}

		byte[] toByteArray() {
			return baos.toByteArray();
		}
	}

	private static class ByteArrayServletStream extends ServletOutputStream {

		ByteArrayOutputStream baos;

		ByteArrayServletStream(ByteArrayOutputStream baos) {
			this.baos = baos;
		}

		@Override
		public void write(int param) throws IOException {
			baos.write(param);
		}
	}

	private static final Logger log = Logger.getLogger(AuthFilter.class);
	private RegistrationHomeService registrationHomeService = null;
	private AuditMobileHomeService auditMobileHomeService = null;
	private NegativeMobileNumberValidator negativeMobileNoValidator = null;
	private InitiateRequestDao initiateRequestDao=null;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		String resp = null;
		Date reqDate = new Date();
		RespJson respJson = new RespJson();
		ReqResp reqResp = null;
		if (null == auditMobileHomeService) {
			auditMobileHomeService = new AuditMobileHomeService();
		}
		boolean authenticated = true;
		final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		XSSRequestWrapper wrappedRequest = new XSSRequestWrapper(httpRequest);
		final ByteArrayPrintWriter pw = new ByteArrayPrintWriter();
		HttpServletResponse wrappedResp = new HttpServletResponseWrapper(response) {
			@Override
			public ServletOutputStream getOutputStream() {
				return pw.getStream();
			}

			@Override
			public PrintWriter getWriter() {
				return pw.getWriter();
			}
		};
		String url = "", authToken = "", reqStr = "", deviceId = "", serverToken = "", source = "", node = "",regId = "",xtoken="";
		String encAuthToken = "", encEncryptionKey = "", encDeviceId = "", encSource = "", encNode = "", encRegId = "",encXtoken="";
		String encryptionKey = "";
		int serviceFlag = 0;
		String requestDesc = "";
		String checkSumKey="",requestId="";
		try {
			url = httpRequest.getPathInfo();
			log.info(url);
			String tempUrl = url.replace("/", "");
			encAuthToken = httpRequest.getHeader("authToken");
			encEncryptionKey = httpRequest.getHeader("perkey");
			encDeviceId = httpRequest.getHeader("deviceId");
			encSource = httpRequest.getHeader("sapp");
			encNode = httpRequest.getHeader("snode");
			encRegId = httpRequest.getHeader("sopay");
			encXtoken = httpRequest.getHeader("xtoken");
			log.info("Encrypted encAuthToken-> " + encAuthToken);
			log.info("Encrypted encEncryptionKey-> " + encEncryptionKey);
			log.info("Encrypted encSource-> " + encSource);
			log.info("Encrypted encNode-> " + encNode);
			log.info("Encrypted encRegId-> " + encRegId);
			log.info("Encrypted Xtoken-> " + encXtoken);
			reqStr = IOUtils.toString(wrappedRequest.getReader());
			log.info("Encrypted REQUEST for authfilter-> " + reqStr);
			if (null == aESEncryptionUtility) {
				aESEncryptionUtility = new AesEncryption();
			}
			if (null == reqResp) {
				reqResp = new ReqResp();
			}
			reqResp = JSONConvert.getReqResp(reqStr);
			log.info("decrypted REQUEST for authfilter -> " + reqResp);
			requestDesc = reqResp.getDesc();
			ReqJson reqJson = null;
			String encKey = ConstantI.KEY_DECRY_HEADER;
			log.info("Encrpted Key-> " + encKey);
			encDeviceId=aESEncryptionUtility.encrypt(encDeviceId, encKey);
			log.info("Encrypted DeviceId-> " + encDeviceId);
			try {
				if (!StringUtils.isEmpty(encAuthToken)) {
					authToken = aESEncryptionUtility.decrypt(encAuthToken, encKey);
				}
				if (!StringUtils.isEmpty(encDeviceId)) {
					deviceId = aESEncryptionUtility.decrypt(encDeviceId, encKey);
				}
				if (!StringUtils.isEmpty(encSource)) {
					source = aESEncryptionUtility.decrypt(encSource, encKey);
				}
				if (!StringUtils.isEmpty(encNode)) {
					node = aESEncryptionUtility.decrypt(encNode, encKey);
				}
				if (!StringUtils.isEmpty(encRegId)) {
					regId = aESEncryptionUtility.decrypt(encRegId, encKey);
				}
				if (!StringUtils.isEmpty(encEncryptionKey)) {
					encryptionKey = aESEncryptionUtility.decrypt(encEncryptionKey, encKey);
				}
				
				if (!StringUtils.isEmpty(encXtoken)) {
					xtoken = aESEncryptionUtility.decrypt(encXtoken, encKey);
				}
				log.info("decrypted xtoken-> " + xtoken);

			} catch (Exception e) {
				StringWriter s;
				e.printStackTrace(new PrintWriter(s = new StringWriter()));
				e.printStackTrace();
				log.error(e);
				log.info("Something went to wrong=" + s);
			}
			if (deviceId != null) {
				deviceId = deviceId.trim();
			}
			if (tempUrl.matches("|MobilegetAppVersion")) {
				serviceFlag = 1;
			} else if (tempUrl.matches(URLMATCH)) {
				serviceFlag = 2;
			} else {
				serviceFlag = 3;
			}
			if (1 == serviceFlag) {
				encryptionKey = deviceId.substring(0, 16);
				checkSumKey=deviceId;
				log.info("Checksum key for AppVersion -> " + checkSumKey);
				log.info("RESP-DATA -> " + reqResp.getData());
				reqStr = aESEncryptionUtility.decrypt(reqResp.getData(), checkSumKey);
				log.info("Data for service flag1 -> " + reqStr);
				log.info("Requested checksum for service flag1 -> " + requestDesc);
				log.info("Xtoken for service flag1 -> " + xtoken);
				String genCheckSum = Util.CalculateChecksum(reqStr, xtoken);
				log.info("Generated checksum for AppVersion -> " + genCheckSum);
				if (Util.compareCheckSum(requestDesc, genCheckSum)) {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_TOKEN_MISMATCH.getCode());
					String finalRespJson = "";
					finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), checkSumKey);
					String responCheckSum = Util.CalculateChecksum(finalRespJson, xtoken);
					log.info("Resposnse Checksum for AppVersion -> " + responCheckSum);
					reqResp = new ReqResp();
					authenticated=false;
					reqResp.setData(finalRespJson);
					reqResp.setDesc(responCheckSum);
					response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
					log.info("Send failure RESPONSE Of Service Flag1 TOKEN_MISMATCH-> " + reqResp);
					response.getOutputStream().close();
					return ;
				}else {
					reqJson = JSONConvert.getReqJson(reqStr);
					requestId=reqJson.getReqId();
					log.info("Receive Request ID-> " + requestId);
					boolean checkFlag=Util.checkRequestId(requestId);
					if(checkFlag) {
						initiateRequestDao=new InitiateRequestDao();
						boolean validateRequest=initiateRequestDao.validateRequest(requestId);
						if(validateRequest) {
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							respJson.setMsg(ErrorCode.AcqErrorCode.ALREADY_INITIATE_REQUEST.getCode());
							String finalRespJson = "";
							finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), checkSumKey);
							String responCheckSum = Util.CalculateChecksum(finalRespJson, xtoken);
							reqResp = new ReqResp();
							authenticated=false;
							reqResp.setData(finalRespJson);
							reqResp.setDesc(responCheckSum);
							response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
							log.info("Send failure RESPONSE Of Service Flag1 Already initiate request-> " + reqResp);
							response.getOutputStream().close();
							return ;
						}else {
							InitiateRequest initiateRequest=new InitiateRequest();
							initiateRequest.setCreateddate(new Date());
							initiateRequest.setDeviceid(deviceId);
							initiateRequest.setRequestId(requestId);
							initiateRequestDao.insertRequest(initiateRequest);
						}
						if ("UPI".equalsIgnoreCase(url.substring(1, 4)) && null != reqJson.getTxnType()) {
							UtilLogger.writeTextFile("authToken=[" + authToken + "] perkey[" + encryptionKey + "] deviceId["+ deviceId.trim() + "] " + reqStr, "Upi/" + reqJson.getTxnType());
						} else {
							UtilLogger.writeTextFile("authToken=[" + authToken + "] perkey[" + encryptionKey + "] deviceId["+ deviceId.trim() + "] " + reqStr, url);
						}
					}else {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ErrorCode.AcqErrorCode.INVALID_REQUEST_ID.getCode());
						String finalRespJson = "";
						finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), checkSumKey);
						String responCheckSum = Util.CalculateChecksum(finalRespJson, xtoken);
						authenticated=false;
						reqResp = new ReqResp();
						reqResp.setData(finalRespJson);
						reqResp.setDesc(responCheckSum);
						response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
						log.info("Send failure RESPONSE Of Service Flag1 invalid requestId-> " + reqResp);
						response.getOutputStream().close();
						return ;
					}
				}
	
				
			
			} else if (2 == serviceFlag) {
				if (null == securityEncryptionService) {
					securityEncryptionService = new SecurityEncryptionService();
				}
				//encryptionKey = securityEncryptionService.getDetailsByDeviceId(deviceId.trim());
				Map<String,String> mapResp = securityEncryptionService.getServerTokenAndDesByDeviceId(deviceId.trim());
				serverToken=mapResp.get("serverToken");
				checkSumKey=mapResp.get("eb");
				log.info("Server token for flag 2 " + serverToken);
				log.info("Check Sum Key for flag 2 " + checkSumKey);
				reqStr = aESEncryptionUtility.decrypt(reqResp.getData(),serverToken);
				log.info("data for service flag2" + reqStr);
				log.info("Requested checksum for service flag2 -> " + requestDesc);
				String genCheckSum = Util.CalculateChecksum(reqStr, checkSumKey);
				if (Util.compareCheckSum(requestDesc, genCheckSum)) {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_TOKEN_MISMATCH.getCode());
					String finalRespJson = "";
					finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), encryptionKey);
					String responCheckSum = Util.CalculateChecksum(finalRespJson, checkSumKey);
					authenticated=false;
					reqResp = new ReqResp();
					reqResp.setData(finalRespJson);
					reqResp.setDesc(responCheckSum);
					response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
					log.info("Send failure RESPONSE of Service Flag2 TOKEN_MISMATCH -> " + reqResp);
					response.getOutputStream().close();
					return ;
				}else {
					reqJson = JSONConvert.getReqJson(reqStr);
					requestId=reqJson.getReqId();
					log.info("Receive Request ID-> " + requestId);
					boolean checkFlag=Util.checkRequestId(requestId);
					if(checkFlag) {
						initiateRequestDao=new InitiateRequestDao();
						boolean validateRequest=initiateRequestDao.validateRequest(requestId);
						if(validateRequest) {
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							respJson.setMsg(ErrorCode.AcqErrorCode.ALREADY_INITIATE_REQUEST.getCode());
							String finalRespJson = "";
							finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), encryptionKey);
							String responCheckSum = Util.CalculateChecksum(finalRespJson, encryptionKey);
							reqResp = new ReqResp();
							authenticated=false;
							reqResp.setData(finalRespJson);
							reqResp.setDesc(responCheckSum);
							response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
							log.info("Send failure RESPONSE Of Service Flag2 already initiate-> " + reqResp);
							response.getOutputStream().close();
							return ;
						}else {
							InitiateRequest initiateRequest=new InitiateRequest();
							initiateRequest.setCreateddate(new Date());
							initiateRequest.setDeviceid(deviceId);
							initiateRequest.setRequestId(requestId);
							initiateRequestDao.insertRequest(initiateRequest);
						}
						
						if ("UPI".equalsIgnoreCase(url.substring(1, 4)) && null != reqJson.getTxnType()) {
							UtilLogger.writeTextFile("authToken=[" + authToken + "] perkey[" + encryptionKey + "] deviceId["+ deviceId.trim() + "] " + reqStr, "Upi/" + reqJson.getTxnType());
						} else {
							UtilLogger.writeTextFile("authToken=[" + authToken + "] perkey[" + encryptionKey + "] deviceId["+ deviceId.trim() + "] " + reqStr, url);
						}
					}else {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ErrorCode.AcqErrorCode.INVALID_REQUEST_ID.getCode());
						String finalRespJson = "";
						finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), encryptionKey);
						String responCheckSum = Util.CalculateChecksum(finalRespJson, encryptionKey);
						reqResp = new ReqResp();
						authenticated=false;
						reqResp.setData(finalRespJson);
						reqResp.setDesc(responCheckSum);
						response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
						log.info("Send failure RESPONSE Of Service Flag2 invalid request-> " + reqResp);
						response.getOutputStream().close();
						return ;
					}
				}
			} else {
				log.info("printing reg id for UPI" + regId);
				log.info("printing device id for searching data" + deviceId);
				//serverToken = registrationHomeService.getServerToken(deviceId.trim(), regId);
				Registration registration = registrationHomeService.getServerToken(deviceId.trim(), regId);
				serverToken=registration.getServertoken();
				String tab_regId=String.valueOf(registration.getRegid());
				String tab_mobile=registration.getMobno();
				log.info("RegId from Registration table " + tab_regId);
				log.info("Mobile from Registration table " + tab_mobile);
				
				Map<String,String> mapResp = securityEncryptionService.getServerTokenAndDesByDeviceId(deviceId.trim());
				//serverToken= securityEncryptionService.getDetailsByDeviceId(deviceId.trim());
				//serverToken=mapResp.get("serverToken");
				checkSumKey=mapResp.get("eb");
				log.info("Server token for flag3 " + serverToken);
				log.info("Check Sum Key for flag3 " + checkSumKey);
				reqStr = aESEncryptionUtility.decrypt(reqResp.getData(), serverToken);
				log.info("data for service flag3 " + reqStr);
				log.info("Requested checksum for service flag3 -> " + requestDesc);
				String genCheckSum = Util.CalculateChecksum(reqStr, checkSumKey);
				if (Util.compareCheckSum(requestDesc, genCheckSum)) {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_TOKEN_MISMATCH.getCode());
					String finalRespJson = "";
					finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), encryptionKey);
					String responseCheckSum = Util.CalculateChecksum(finalRespJson, checkSumKey);
					authenticated=false;
					reqResp = new ReqResp();
					reqResp.setData(finalRespJson);
					reqResp.setDesc(responseCheckSum);
					response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
					log.info("Send failure RESPONSE of service Flag3 MSGID_TOKEN_MISMATCH-> " + reqResp);
					response.getOutputStream().close();
					return ;
				}else {
					reqJson = JSONConvert.getReqJson(reqStr);
					String body_regid=reqJson.getRegId();
					String body_mobile=reqJson.getMobileNo();
					
					log.info("RegId from body " + body_regid);
					log.info("Mobile from body" + body_mobile);
					if(!tab_regId.equalsIgnoreCase(body_regid)|| !tab_mobile.equalsIgnoreCase(body_mobile)) {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ErrorCode.AcqErrorCode.INVALID_REQUEST.getCode());
						String finalRespJson = "";
						finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), serverToken);
						String responCheckSum = Util.CalculateChecksum(finalRespJson, encryptionKey);
						reqResp = new ReqResp();
						authenticated=false;
						reqResp.setData(finalRespJson);
						reqResp.setDesc(responCheckSum);
						response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
						log.info("Send failure RESPONSE Of Service Flag2 invalid request-> " + reqResp);
						response.getOutputStream().close();
						return ;
					}else {
						requestId=reqJson.getReqId();
						log.info("Receive Request ID-> " + requestId);
						boolean checkFlag=Util.checkRequestId(requestId);
						if(checkFlag) {
							initiateRequestDao=new InitiateRequestDao();
							boolean validateRequest=initiateRequestDao.validateRequest(requestId);
							if(validateRequest) {
								respJson.setMsgId(ConstantI.MSGID_FAIL);
								respJson.setMsg(ErrorCode.AcqErrorCode.ALREADY_INITIATE_REQUEST.getCode());
								String finalRespJson = "";
								finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), encryptionKey);
								String responCheckSum = Util.CalculateChecksum(finalRespJson, encryptionKey);
								reqResp = new ReqResp();
								authenticated=false;
								reqResp.setData(finalRespJson);
								reqResp.setDesc(responCheckSum);
								response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
								log.info("Send failure RESPONSE Of Service Flag3 already initiate request-> " + reqResp);
								response.getOutputStream().close();
								return ;
							}else {
								InitiateRequest initiateRequest=new InitiateRequest();
								initiateRequest.setCreateddate(new Date());
								initiateRequest.setDeviceid(deviceId);
								initiateRequest.setRequestId(requestId);
								initiateRequestDao.insertRequest(initiateRequest);
							}
							
							if ("UPI".equalsIgnoreCase(url.substring(1, 4)) && null != reqJson.getTxnType()) {
								UtilLogger.writeTextFile("authToken=[" + authToken + "] perkey[" + encryptionKey + "] deviceId["+ deviceId.trim() + "] " + reqStr, "Upi/" + reqJson.getTxnType());
							} else {
								UtilLogger.writeTextFile("authToken=[" + authToken + "] perkey[" + encryptionKey + "] deviceId["+ deviceId.trim() + "] " + reqStr, url);
							}
						}else {
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							respJson.setMsg(ErrorCode.AcqErrorCode.INVALID_REQUEST_ID.getCode());
							String finalRespJson = "";
							finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), encryptionKey);
							String responCheckSum = Util.CalculateChecksum(finalRespJson, encryptionKey);
							reqResp = new ReqResp();
							authenticated=false;
							reqResp.setData(finalRespJson);
							reqResp.setDesc(responCheckSum);
							response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
							log.info("Send failure RESPONSE Of Service Flag3 invalid request-> " + reqResp);
							response.getOutputStream().close();
							return ;
						}
					}
			
				}
	
			}
			if (reqStr.isEmpty()) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
				String finalRespJson = "";
				if (1 == serviceFlag || 2 == serviceFlag) {
					finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), encryptionKey);
				} else {
					finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), serverToken);
				}
				
				log.info("Value of finalRespJson reqStr is empty -> "+finalRespJson);
				log.info("Value of checkSumKey reqStr is empty-> "+checkSumKey);
				String genCheckSum = Util.CalculateChecksum(finalRespJson, checkSumKey);
				log.info("Generated checkSum reqStr is empty-> "+genCheckSum);
				authenticated=false;
				reqResp = new ReqResp();
				reqResp.setData(finalRespJson);
				reqResp.setDesc(genCheckSum);
				response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
				log.info("Send to Final RESPONSE -> " + JSONConvert.getJsonStr(reqResp).getBytes());
				response.getOutputStream().close();
				return ;
			} else if (null != reqJson.getMobileNo()&& !(reqJson.getMobileNo().startsWith("91") && 12 == reqJson.getMobileNo().length())) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
				String finalRespJson = "";
				if (1 == serviceFlag || 2 == serviceFlag) {
					finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), encryptionKey);
				} else {
					finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), serverToken);
				}
				log.info("Value of finalRespJson MSGID_EXCEPTIONMSG -> "+finalRespJson);
				log.info("Value of checkSumKey MSGID_EXCEPTIONMSG-> "+checkSumKey);
				String genCheckSum = Util.CalculateChecksum(finalRespJson, checkSumKey);
				log.info("Generated checkSum MSGID_EXCEPTIONMSG-> "+genCheckSum);
				reqResp = new ReqResp();
				authenticated=false;
				reqResp.setData(finalRespJson);
				reqResp.setDesc(genCheckSum);
				response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
				log.info("Send to Final RESPONSE -> " + JSONConvert.getJsonStr(reqResp).getBytes());
				response.getOutputStream().close();
				return ;

			} else {
				boolean flagNegativeNo = false;
				try {
					if (!StringUtils.isEmpty(reqJson.getMobileNo())) {
						flagNegativeNo = negativeMobileNoValidator.validate(reqJson.getMobileNo());
					}
				} catch (Exception e) {
					StringWriter s;
					e.printStackTrace(new PrintWriter(s = new StringWriter()));
					e.printStackTrace();
					log.error(s);
				}
				if (flagNegativeNo) {
					log.info("This is a negative mobile number : " + reqJson.getMobileNo());
					authenticated = false;
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_REG_NEGATIVE_MOBILE_NO.getCode());
				} else {
				}
				log.info("value of authenticated is="+authenticated);
				if (authenticated) {
					log.info("authenticated true");
					wrappedRequest.resetInputStream(reqStr.getBytes());
					filterChain.doFilter(wrappedRequest, wrappedResp);
					byte[] bytes = pw.toByteArray();
					resp = new String(bytes);
					JSONConvert.getReqJson(resp);
					String finalRespJson = "";
					String genCheckSum="";
					if (1 == serviceFlag) {
						log.info("Final response for flag1->" + resp);
						log.info("Encryption key for sending response flag1->" + xtoken);
						finalRespJson = aESEncryptionUtility.encrypt(resp, xtoken);
						log.info("Value of finalRespJson for flag1-> "+finalRespJson);
						log.info("Xtoken Value for Generate Check Sum flag1-> "+xtoken);
						genCheckSum = Util.CalculateChecksum(resp, xtoken);
						log.info("Generated Check Sum flag1-> "+genCheckSum);
					} else if(2==serviceFlag){
							 Map<String,String> mapResp = securityEncryptionService.getServerTokenAndDesByDeviceId(deviceId.trim());
							 encryptionKey=mapResp.get("serverToken");
							 checkSumKey=mapResp.get("eb");
						     log.info("Encryption key for sending response flag2->" + encryptionKey);
						     finalRespJson = aESEncryptionUtility.encrypt(resp, encryptionKey);
						     log.info("Value of response checkSumKey flag2-> "+checkSumKey);
						     genCheckSum = Util.CalculateChecksum(resp, checkSumKey);
					}else {
						log.info("Server token for sending response flag3->" + serverToken);
						finalRespJson = aESEncryptionUtility.encrypt(resp, serverToken);
						log.info("Value of response checkSumKey flag3-> "+checkSumKey);
						genCheckSum = Util.CalculateChecksum(resp, checkSumKey);
					}
					log.info("Value of finalRespJson-> "+finalRespJson);
					log.info("Generated checkSum-> "+genCheckSum);
					reqResp = new ReqResp();
					reqResp.setData(finalRespJson);
					reqResp.setDesc(genCheckSum);
					response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
					log.info("Send RESPONSE -> " + respJson);
					log.info("Send to Final RESPONSE -> " + JSONConvert.getJsonStr(reqResp).getBytes());
					if ("UPI".equalsIgnoreCase(url.substring(1, 4)) && null != reqJson.getTxnType()) {
						UtilLogger.writeTextFile("authToken=[" + authToken + "] perkey[" + encryptionKey + "] deviceId["+ deviceId.trim() + "] " + reqStr, "Upi/" + reqJson.getTxnType());
					} else {
						UtilLogger.writeTextFile("authToken=[" + authToken + "] perkey[" + encryptionKey + "] deviceId["+ deviceId.trim() + "] " + reqStr, url);
					}
					UtilLogger.writeTextFile("authToken=[" + authToken + "] perkey[" + encryptionKey + "] deviceId["+ deviceId.trim() + "] " + resp, url);
				} 
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(e);
			log.info("Something went to wrong=" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			String finalRespJson = "";
			if (1 == serviceFlag || 2 == serviceFlag) {
				finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), encryptionKey);
			} else {
				finalRespJson = aESEncryptionUtility.encrypt(JSONConvert.getJsonStr(respJson), serverToken);
			}
			log.info("Value of finalRespJson of catch block-> "+finalRespJson);
			log.info("Value of checkSumKey of catch block-> "+checkSumKey);
			String responCheckSum = Util.CalculateChecksum(finalRespJson, checkSumKey);
			log.info("Generated checkSum-> "+responCheckSum);
			reqResp = new ReqResp();
			reqResp.setData(finalRespJson);
			reqResp.setDesc(responCheckSum);
			response.getOutputStream().write(JSONConvert.getJsonStr(reqResp).getBytes());
			log.debug("RESPONSE FROM CATCH -> " + JSONConvert.getJsonStr(respJson));
			response.getOutputStream().close();
			return ;
		} finally {
			if (null != resp) {
				auditMobileHomeService.auditLogs(reqStr, resp, reqDate, new Date(), url, authToken, source, node);
			} else {
				auditMobileHomeService.auditLogs(reqStr, JSONConvert.getJsonStr(reqResp), reqDate, new Date(), url,authToken, source, node);
			}
			response.getOutputStream().close();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (null == registrationHomeService) {
			registrationHomeService = new RegistrationHomeService();
		}
		if (null == auditMobileHomeService) {
			auditMobileHomeService = new AuditMobileHomeService();
		}

		if (null == negativeMobileNoValidator) {
			negativeMobileNoValidator = new NegativeMobileNumberValidator();
		}
	}

}
