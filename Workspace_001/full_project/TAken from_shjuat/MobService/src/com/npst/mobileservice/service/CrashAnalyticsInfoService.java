/**
 * 
 */
package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.CrashAnalyticsInfoDao;
import com.npst.mobileservice.object.CrashAnalyticsInfoRequest;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.CrashAnalyticsInfoUtil;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.upi.hor.CrashAnalyticsInfo;

/**
 * @author npst
 *
 */
public class CrashAnalyticsInfoService {

	private static final Logger log = Logger.getLogger(CrashAnalyticsInfoService.class);
	private static CrashAnalyticsInfoDao exceptionLogDao = null;

	public RespJson addExceptionLog(final String strBuild) {
		log.info("Servicing request for crash analytics : " + strBuild);
		CrashAnalyticsInfoRequest request = (CrashAnalyticsInfoRequest) JSONConvert.convertToObject(strBuild,
				CrashAnalyticsInfoRequest.class);
		final RespJson respJson = new RespJson();
		
		CrashAnalyticsInfo crashAnalyticsInfo = null;
		Long serializable = null;
		try {
			if (null == exceptionLogDao) {
				exceptionLogDao = new CrashAnalyticsInfoDao();
			}
			crashAnalyticsInfo = CrashAnalyticsInfoUtil.convertToEntity(request);
			serializable = exceptionLogDao.addExceptionLog(crashAnalyticsInfo);
			if (serializable != null && serializable.longValue() > 0L) {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
			}
		} catch (Exception e) {
			StringWriter s;
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		return respJson;
	}

}
