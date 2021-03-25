/**
 * 
 */
package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.AppVersionDao;
import com.npst.mobileservice.dao.SilentsmsHome;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.Appversion;
import com.npst.upi.hor.Silentsms;

/**
 * @author npst
 */
public class AppVersionService {
	private static final Logger log = Logger.getLogger(AppVersionService.class);

	private static final int ACTIVE = 1;
	private static AppVersionDao appVersionDao = new AppVersionDao();
	private static String firstMobCall = Util.getProperty("FIRSTMOBCALL");
	private static String longCode = Util.getProperty("LONGCODE");
	private static String forConsecutiveCall = Util.getProperty("FORCONSECUTIVECALL");
	private static String mobCallNo = Util.getProperty("MOBCALLNO");
	private static String passregexmulti = Util.getProperty("pass_regex_multi");
	private static String passregexseq = Util.getProperty("pass_regex_seq");
	private static String isPassRegex = Util.getProperty("isPassRegex");
	private static String gmFirstMobCall = Util.getProperty("GMFIRSTMOBCALL");
	private static String gmForConsecutiveCall = Util.getProperty("GMFORCONSECUTIVECALL");
	private static String gmMobCallNo = Util.getProperty("GMMOBCALLNO");
	private static SilentsmsHome silentsmsServiceDao = null;

	public RespJson getAppVersion(ReqJson reqJson, String deviceId) {

		RespJson respJson = new RespJson();
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_WRONG_APP_VER.getCode());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		String appOs = "";
		try {
			respJson.setReqId(reqJson.getReqId());
			if (null == appVersionDao) {
				appVersionDao = new AppVersionDao();
			}
			appOs = reqJson.getAppOs().toUpperCase();
			if (appOs.contains("ANDROID")) {
				appOs = reqJson.getAppOs().substring(0, 7);
			} else {
				appOs = reqJson.getAppOs().substring(0, 3);
			}
			List<Appversion> appversion = appVersionDao.findByStatus();
			if (!appversion.isEmpty()) {
				for (Appversion appversion2 : appversion) {
					if (appOs.equalsIgnoreCase(appversion2.getAppos())) {
						if (reqJson.getAppVer().equalsIgnoreCase(appversion2.getAppversion())) {

							if ("1".equalsIgnoreCase(reqJson.getAppInstall())) {
								final String tid = Util.tidGen();
								log.info("tid for inserting into silentSms entity:{}"+tid.trim());
								log.info("device id for inserting into silentSms entity:{}"+deviceId.trim());
								Silentsms silentSMS = new Silentsms();
								silentSMS.setTid(tid.trim());
								silentSMS.setCircle("");
								silentSMS.setMobno("");
								silentSMS.setDeviceid(deviceId.trim());
								silentSMS.setLogdate(new Date());
								silentSMS.setStatus(SilentSMSStatus.PENDING.getStatus());
								if (null == silentsmsServiceDao) {
									silentsmsServiceDao = new SilentsmsHome();
								}
								silentsmsServiceDao.insertLog(silentSMS);
								log.info("response tid for app version:{}"+tid);
								respJson.setTid(tid);
							}
							respJson.setUpgradeType(appversion2.getAction());
							respJson.setUpgradeUrl(appversion2.getAppurl());
							respJson.setMsgId(ConstantI.MSGID_SUCCESS);
							respJson.setMsg(ConstantI.SUCCESS_STRING);
							break;
						}
					}
				}
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
			}
			respJson.setFirstMobCall(firstMobCall);
			respJson.setForConsecutiveCall(forConsecutiveCall);
			respJson.setMobCallNo(mobCallNo);
			respJson.setLongCode(longCode);
			respJson.setPassregexmulti(passregexmulti);
			respJson.setPassregexseq(passregexseq);
			respJson.setIsPassRegex(isPassRegex);

			respJson.setGmFirstMobCall(gmFirstMobCall);
			respJson.setGmForConsecutiveCall(gmForConsecutiveCall);
			respJson.setGmmobCallNo(gmMobCallNo);

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return respJson;
	}
	
	
	public RespJson getAppVerOtherPsp(ReqJson reqJson, String deviceId) {
		RespJson respJson = new RespJson();
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_WRONG_APP_VER.getCode());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			if (null == appVersionDao) {
				appVersionDao = new AppVersionDao();
			}
			List<Appversion> appversion = appVersionDao.findByStatus();
			if (0 != appversion.size()) {
				for (Appversion appversion2 : appversion) {
					if ("1".equalsIgnoreCase(reqJson.getAppInstall())) {
						final String tid = Util.tidGen();
						Silentsms silentSMS = new Silentsms();
						silentSMS.setTid(tid);
						silentSMS.setCircle("");
						silentSMS.setMobno("");
						silentSMS.setDeviceid(deviceId);
						silentSMS.setLogdate(new Date());
						silentSMS.setStatus(SilentSMSStatus.PENDING.getStatus());
						silentSMS.setSource(reqJson.getSource());
						silentSMS.setNode(reqJson.getNode());
						if (null == silentsmsServiceDao) {
							silentsmsServiceDao = new SilentsmsHome();
						}
						silentsmsServiceDao.insertLog(silentSMS);
						respJson.setTid(tid);
					}
					//respJson.setUpgradeType(appversion2.getAction());
					//respJson.setUpgradeUrl(appversion2.getAppurl());
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
					respJson.setMsg(ConstantI.SUCCESS_STRING);
					break;
				}
			} else {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_REC_FOUND.getCode());
			}
//			respJson.setFirstMobCall(firstMobCall);
//			respJson.setForConsecutiveCall(forConsecutiveCall);
//			respJson.setMobCallNo(mobCallNo);
//			respJson.setLongCode(longCode);
//			respJson.setPassregexmulti(passregexmulti);
//			respJson.setPassregexseq(passregexseq);
//			respJson.setIsPassRegex(isPassRegex);
//
//			respJson.setGmFirstMobCall(gmFirstMobCall);
//			respJson.setGmForConsecutiveCall(gmForConsecutiveCall);
//			respJson.setGmmobCallNo(gmMobCallNo);
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			log.info(s);
		}
		return respJson;
	}
}
