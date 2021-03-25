package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.SecurityEncryptionDao;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.Securityencryption;

public class SecurityEncryptionService {
	private static final Logger log = Logger.getLogger(SecurityEncryptionService.class);
	private static SecurityEncryptionDao securityEncryptionDao = null;

	DateFormat dfDob = new SimpleDateFormat("dd/MM/yyyy");

	public RespJson insertDeviceId(String deviceId, RespJson respJson) {
		log.info("reqStr:" + deviceId);
		String serverToken = "";
		String desc = "";
		try {
			if (null == securityEncryptionDao) {
				securityEncryptionDao = new SecurityEncryptionDao();
			}
			Securityencryption securityencryption = null;
			Securityencryption getSecurityEncryption = securityEncryptionDao.getUserByDeviceId(deviceId.trim());
			boolean flag = false;
			if (null == getSecurityEncryption) {
				securityencryption = new Securityencryption();
				securityencryption.setEncryptionkey(Util.uuidGen());
				serverToken = securityencryption.getEncryptionkey();
				securityencryption.setStatus(1);
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(new Date());
					c.add(Calendar.MINUTE, Integer.parseInt(Util.getProperty("SERVERTOKENTIME")));
				} catch (Exception e) {
					StringWriter s;
					e.printStackTrace(new PrintWriter(s = new StringWriter()));
					e.printStackTrace();
					log.info(s);
					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
					respJson.setChEnable(true);
					return respJson;
				}
				securityencryption.setValidtil(c.getTime());
				securityencryption.setCreateddate(new Date());
				securityencryption.setDeviceid(deviceId.trim());
				log.info("Insert data for SecurityEncryption table="+securityencryption);
				flag = securityEncryptionDao.insert(securityencryption);
			} else {
				getSecurityEncryption.setEncryptionkey(Util.uuidGen());
				serverToken = getSecurityEncryption.getEncryptionkey();
				getSecurityEncryption.setUpdateddate(new Date());
				log.info("Update data for SecurityEncryption table="+getSecurityEncryption);
				flag = securityEncryptionDao.update(getSecurityEncryption);
			}
			if (flag) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setServerToken(serverToken);
				respJson.setEb(serverToken.substring(3,10));
				respJson.setChEnable(true);
			} else {
				respJson.setEb(serverToken.substring(3,10));
				respJson.setChEnable(true);
				respJson.setServerToken(serverToken);
				respJson.setMsgId(ConstantI.MSGID_FAIL);
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setEb(serverToken.substring(3,10));
			respJson.setServerToken(serverToken);
			respJson.setChEnable(true);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		respJson.setChEnable(true);
		log.info("Value of ChEnable:" + respJson.isChEnable());
		log.info("Value of Eb:" + respJson.getEb());
		log.info("return successfully with respJson for insertDeviceId:" + respJson);
		return respJson;
	}

	public String getDetailsByDeviceId(String deviceId) {
		log.info("Device id for getDetailsByDeviceId="+deviceId);
		String serverToken = "";
		try {
			if (null == securityEncryptionDao) {
				securityEncryptionDao = new SecurityEncryptionDao();
			}
			Securityencryption resultRegVpa = securityEncryptionDao.getUserByDeviceId(deviceId.trim());
			if (null != resultRegVpa) {
				serverToken = resultRegVpa.getEncryptionkey();
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return serverToken;
	}
	
	
	
	
	public Map<String,String> getServerTokenAndDesByDeviceId(String deviceId) {
		log.info("Device id for getDetailsByDeviceId="+deviceId);
		String serverToken = "",desc="";
		Map<String,String> mapresp=new HashMap<String,String>();
		
		try {
			if (null == securityEncryptionDao) {
				securityEncryptionDao = new SecurityEncryptionDao();
			}
			Securityencryption resultRegVpa = securityEncryptionDao.getUserByDeviceId(deviceId.trim());
			if (null != resultRegVpa) {
				serverToken = resultRegVpa.getEncryptionkey();
				desc = serverToken.substring(3,10);
			}
			mapresp.put("serverToken", serverToken);
			mapresp.put("eb", desc);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return mapresp;
	}


}
