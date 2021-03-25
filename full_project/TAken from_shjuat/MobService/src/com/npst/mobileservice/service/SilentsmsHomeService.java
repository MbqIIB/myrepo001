package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.SilentsmsHome;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.AesEncryption;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.Silentsms;

public class SilentsmsHomeService {
	private static SilentsmsHome silentsmsServiceDao = null;
	private static final Logger log = Logger.getLogger(SilentsmsHomeService.class);
	private static String lastMobSecond = Util.getProperty("LASTMOBSECONDGETMOBILE");

	public RespJson getMobileNoService(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXPIRE_SMS.getCode());
		respJson.setReqId(reqJson.getReqId());
		try {
			if (null == silentsmsServiceDao) {
				silentsmsServiceDao = new SilentsmsHome();
			}
			List<Silentsms> silentSmsList = silentsmsServiceDao.getMobileNo(reqJson);
			log.info("silentSmsList size is as :" + silentSmsList.size());
			if (!silentSmsList.isEmpty()) {
				final Silentsms silentsms = silentSmsList.get(0);
				// check societyNo
				log.info("Requested Mobile No :" + reqJson.getSocietyNo());
				log.info("Silent SMS Mobile No :" +silentsms.getMobno());
				String mobile=Util.mobileNoParse(reqJson.getSocietyNo());
				log.info("Parsing Mobile No :" +mobile);
				if(!mobile.equalsIgnoreCase(silentsms.getMobno())) {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.SOCITY_NO_MISMATCH.getCode());
					return respJson;
				}
				if(ConstantI.SILENT_SMS_EXPIRED == silentsms.getStatus()) {
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXPIRE_SMS.getCode());
					return respJson;
				}
				
				LocalDateTime current = LocalDateTime.now();
				Instant instant = silentsms.getLogdate().toInstant();
				ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
				LocalDateTime logdt = zdt.toLocalDateTime();
				log.info("Current date ::::" + current);
				//final long timesecs = Long.parseLong(lastMobSecond);
				long timesecs = Long.parseLong(Util.getProperty("LASTMOBSECONDGETMOBILE"));
				log.info("LASTMOBSECONDGETMOBILE"+timesecs );
				logdt = logdt.plusSeconds(timesecs);
				log.info("log date ::::" + logdt);
				if (current.isBefore(logdt)) {
					respJson.setMobileNo(silentsms.getMobno());
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
					respJson.setImei(reqJson.getImei());
					respJson.setDeviceId(reqJson.getDeviceId());
					respJson.setTid(reqJson.getTid());

				}
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NOT_RECMOBNO_STRING.getCode());
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			log.info(s);
		}
		return respJson;
	}

	public RespJson getDevicaeIdAndImei(String mobileNo) {
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			if (null == silentsmsServiceDao) {
				silentsmsServiceDao = new SilentsmsHome();
			}
			List<Silentsms> silentSmsList = silentsmsServiceDao.getDeviceIdAndImei(mobileNo);
			if (0 != silentSmsList.size()) {
				for (Silentsms silentsms : silentSmsList) {
					respJson.setDeviceId(silentsms.getDeviceid());
					respJson.setImei(silentsms.getImei());
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
					break;
				}
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NOT_RECMOBNO_STRING.getCode());
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return respJson;
	}

	public void smsLog(HttpServletRequest request) {
		log.info(request.getParameterMap());
	}

	public void smsLog(String keyword, String phonecode, String location, String carrier, String content, String msisdn,
			String timestamp) {
		log.info("Enter silentsmsHomeService for mobileNoSend");
		List<String> deviceDetails2 = new ArrayList<>();
		String deviceDetails[] = new String[2];
		long silenttime = Long.parseLong(Util.getProperty("SILENTSMSSECOND"));
		log.info("validate silent sms in second  : " + silenttime);
		try {
			deviceDetails = content.split("\\ ");
			log.info("Device Details before split : " + deviceDetails);
		} catch (Exception e) {
		}
		try {
			deviceDetails2 = Util.strSplit(deviceDetails[1], '~');
			log.info("Device Details after split : " + deviceDetails2.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Something went wronng while splite device Details: " + deviceDetails2.toString());
		}
		if (null == silentsmsServiceDao) {
			silentsmsServiceDao = new SilentsmsHome();
		}

		log.info("First Parameter for searching data(TID):"+deviceDetails2.get(2).trim());
		log.info("Second Parameter for searching data(DeviceID):"+deviceDetails2.get(1).trim());
		final Silentsms silentsms = silentsmsServiceDao.findByTidAndDeviceId(deviceDetails2.get(2).trim(), deviceDetails2.get(1).trim());
		log.info("SilentSMS data is for MobileToSend: " + silentsms);
		if (silentsms != null) {
			try {

				try {
					silentsms.setCircle(location);
				} catch (Exception e) {
				}
				try {
					silentsms.setDeviceid(deviceDetails2.get(1));
				} catch (Exception e) {
				}
				try {
					silentsms.setTid(deviceDetails2.get(2));
				} catch (Exception e) {
				}
				try {
					silentsms.setImei(deviceDetails2.get(0));
				} catch (Exception e) {
				}
				try {
					silentsms.setHeader(keyword + "|" + phonecode);
				} catch (Exception e) {
				}
				try {
					silentsms.setSentdate(timestamp);
				} catch (Exception e) {
				}
//				try {
//					silentsms.setLogdate(new Date());
//				} catch (Exception e) {
//				}
				try {
					silentsms.setMobno(msisdn);
				} catch (Exception e) {
				}
				try {
					silentsms.setOperator(carrier);
				} catch (Exception e) {
				}
				try {
					silentsms.setSmstext(content);
				} catch (Exception e) {
				}

				silentsms.setStatus(ConstantI.SILENT_SMS_ACTIVE);
				
				int r = silentsmsServiceDao.updateSilentData(deviceDetails2.get(1), deviceDetails2.get(0),
						deviceDetails2.get(2));
				if (r >= 0) {
					LocalDateTime current = LocalDateTime.now();

					Instant instant = silentsms.getLogdate().toInstant();
					ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
					LocalDateTime logdt = zdt.toLocalDateTime();
					log.info("Current date for silent SMS::::" + current);
					logdt = logdt.plusSeconds(silenttime);
					log.info("log date for silent SMS ::::" + logdt);

					if (!current.isBefore(logdt)) {
						log.info("Something fraudulent detected");
						silentsms.setStatus(ConstantI.SILENT_SMS_EXPIRED);
					}
					silentsmsServiceDao.updateLog(silentsms);

				}
			} catch (Exception e) {
				StringWriter s;
				e.printStackTrace(new PrintWriter(s = new StringWriter()));
				e.printStackTrace();
				log.info(s);
			}
		} else {
			log.info("Fraud detected for device : " + deviceDetails2.get(1) + "  " + deviceDetails2.get(0) + "  "
					+ deviceDetails2.get(2));
		}

	}

	public RespJson updateDeviceIMEI(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setReqId(reqJson.getReqId());
		log.info("Inside to update deviceid :" + reqJson.getDeviceId());
		log.info("Inside to update imei :" + reqJson.getImei());
		try {
			if (null == silentsmsServiceDao) {
				silentsmsServiceDao = new SilentsmsHome();
			}
			int r = silentsmsServiceDao.updateSilentData(reqJson.getDeviceId(), reqJson.getImei(), reqJson.getTid());
			if (r >= 1) {
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_UPDT_DEV_IMEI_SUCCESS.getCode());
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			} else if(r == 0) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NOT_RECMOBNO_STRING.getCode());
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return respJson;
	}

	/*public RespJson disableTid(String tid) {
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			if (null == silentsmsServiceDao) {
				silentsmsServiceDao = new SilentsmsHome();
			}
			Silentsms silentsms = silentsmsServiceDao.findByTid(tid);

			if (silentsms != null) {
				if (silentsms.getStatus() == SilentSMSStatus.PENDING.getStatus()
						|| silentsms.getStatus() == SilentSMSStatus.ACTIVE.getStatus()) {
					silentsms.setStatus(SilentSMSStatus.INACTIVE.getStatus());
					silentsmsServiceDao.updateLog(silentsms);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_UPDT_DEV_IMEI_SUCCESS.getCode());
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				}
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NOT_RECMOBNO_STRING.getCode());
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		}
		return respJson;
	}*/
}
