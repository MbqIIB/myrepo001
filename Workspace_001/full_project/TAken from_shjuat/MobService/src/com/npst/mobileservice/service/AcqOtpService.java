package com.npst.mobileservice.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.AcqOtpDao;
import com.npst.mobileservice.object.Otp;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.CryptoJSImplementation;
import com.npst.mobileservice.util.GupShupSms;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.RabbitMQSend;
import com.npst.mobileservice.util.Util;
import com.npst.mobileservice.util.UtilLogger;
import com.npst.upi.hor.AcqOtpRecord;

public class AcqOtpService {
	private static final Logger log = Logger.getLogger(AcqOtpService.class);
	private static String ACQ_OTP_QUEUE_NAME = Util.getProperty("ACQ_OTP_QUEUE_NAME");
	private static String ACQ_OTP_HTTP_URL = Util.getProperty("ACQ_OTP_HTTP_URL");
	private static String HARD_OTP = Util.getProperty("HARD_OTP");
	private static String ACQ_OTP_LOG = "AcqOtpSend";
	private static AcqOtpDao acqOtpDao;

	private static String ACQ_OTP_TIME = Util.getProperty("ACQ_OTP_TIME");
	
	public void send(ReqJson reqJson) {
		log.trace(JSONConvert.getJsonStr(reqJson));
		String otpStr = null;
		try {
			if (null == acqOtpDao) {
				acqOtpDao = new AcqOtpDao();
			}
			otpStr = Util.generateOtp(ConstantI.otpLen);
			otpStr=HARD_OTP;
		    log.trace("your OTP="+otpStr);
			acqOtpDao.getOnMobileNoAndIsUsedAndUpdate(reqJson.getMobileNo(), 0);
			AcqOtpRecord acqOtpRecord = new AcqOtpRecord();
			acqOtpRecord.setCreTime(new Date());
			acqOtpRecord.setExpiryTime(DateUtils.addMinutes(new Date(), Integer.parseInt(ACQ_OTP_TIME)));
			acqOtpRecord.setFailAttempt(0);
			acqOtpRecord.setMaxFailAttempt(ConstantI.MAX_FAIL_ATTEMPT);
			acqOtpRecord.setMobileNo(reqJson.getMobileNo());
			acqOtpRecord.setOtp(CryptoJSImplementation.getInstance().encrypt(otpStr));
			acqOtpRecord.setReason(reqJson.getTxnNote());
			acqOtpRecord.setReSendCount(0);
			acqOtpRecord.setWhoReq(reqJson.getPayerAddr());
			acqOtpRecord.setResponse("");
			acqOtpRecord.setIsUsed(0);
			log.info("OTP Record [" + acqOtpRecord + "] for saving into tables");
			acqOtpDao.save(acqOtpRecord);
			GupShupSms.send(reqJson.getMobileNo(), ConstantI.OTP_TEXT + otpStr + ConstantI.OTP_TEXT_LAST);
			log.info("SMS Process Method call for sending the SMS with Data is [" + reqJson + "] and otp is [" + otpStr+ "]");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);

		}

		Otp otp = new Otp();
		try {
			otp.setMessage("your otp is " + otpStr);
			otp.setMobileNo(reqJson.getMobileNo());
			otp.setModule(reqJson.getMode());
			otp.setType(reqJson.getType());
			RabbitMQSend.send(JSONConvert.getJsonStr(otp), ACQ_OTP_QUEUE_NAME);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			sendHttp(otp);

		}

	}

	public void sendHttp(Otp otp) {
		log.trace("data[" + JSONConvert.getJsonStr(otp) + "]");
		String data = JSONConvert.getJsonStr(otp);
		StringBuffer outputSB = null;
		HttpURLConnection conn = null;
		try {
			URL url;
			UtilLogger.writeTextFile(data, ACQ_OTP_LOG);
			log.info(ACQ_OTP_HTTP_URL);
			url = new URL(ACQ_OTP_HTTP_URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("accept", "application/json");
			conn.setRequestProperty("cache-control", "no-cache");
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();
			int responseCode = conn.getResponseCode();
			log.info("The response code from the Notification server is:" + responseCode);
			InputStream inputStream = null;
			if (responseCode == 200) {
				inputStream = conn.getInputStream();
			} else {
				inputStream = conn.getErrorStream();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String output = null;
			outputSB = new StringBuffer();
			while ((output = br.readLine()) != null) {
				outputSB.append(output);
			}
			UtilLogger.writeTextFile(outputSB.toString(), ACQ_OTP_LOG);
			log.info("This is the output from Notification : " + outputSB.toString());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			log.error(s);
		} finally {
			if (null != conn) {
				conn.disconnect();
				conn = null;
			}
		}

	}

	public RespJson validate(ReqJson reqJson,boolean flag) {
		log.info("Starting the validate [" + reqJson + "]");
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		try {
			String regMobile = reqJson.getMobileNo();
			log.debug("regMobile" + regMobile);
			if (null == acqOtpDao) {
				acqOtpDao = new AcqOtpDao();
			}
			List<AcqOtpRecord> otpRecords = acqOtpDao.getOnMobileNoAndIsUsed(regMobile, 0);
			log.info("OTP found  in DB --->" + otpRecords);
			
			if (otpRecords.isEmpty()) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg("INVALID OTP");
				return respJson;
			} else {
				for (AcqOtpRecord AcqOtpRecord : otpRecords) {
					Date expiryTime = AcqOtpRecord.getExpiryTime();
					Date nowTime = new Date();
					if (nowTime.compareTo(expiryTime) > 0) {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg("Otp Expired");
					} else {
						if (AcqOtpRecord.getFailAttempt() >= AcqOtpRecord.getMaxFailAttempt()) {
							respJson.setMsgId(ConstantI.MSGID_FAIL);
							
							respJson.setMsg("Otp Limit Exceed");
						} else {
							String plainOTP = null;
							plainOTP = CryptoJSImplementation.getInstance().decrypt(AcqOtpRecord.getOtp());
							log.info("Plain OTP ---->" + plainOTP);
							if (plainOTP.equals(reqJson.getOtp())) {
								AcqOtpRecord.setIsUsed(1);
								respJson.setMsgId(ConstantI.MSGID_SUCCESS);
								
								respJson.setMsg(ConstantI.SUCCESS_STRING);
							} else {
								AcqOtpRecord.setFailAttempt(AcqOtpRecord.getFailAttempt() + 1);
								respJson.setMsgId(ConstantI.MSGID_FAIL);
								respJson.setMsg("INVALID OTP");
								flag=true;
							}
						}
					}
					if(flag) {
						acqOtpDao.update(AcqOtpRecord);
					}
				}
			}
		} catch (Exception e) {
			log.info("Exception Caught at validate Method is " + e);
			e.printStackTrace();
		} finally {

		}
		return respJson;
	}
}
