package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.LovHome;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.upi.hor.Lov;

public class LovHomeService {
	private static final Logger log = Logger.getLogger(LovHomeService.class);
	LovHome lovHome = null;

	public RespJson selectLovByLovType(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setReqId(reqJson.getReqId());
		respJson.setMsgId(ConstantI.MSGID_SUCCESS);
		respJson.setMsg(ConstantI.SUCCESS_STRING);
		List<Lov> list = null;
		try {
			if (null == lovHome) {
				lovHome = new LovHome();
			}
			list = lovHome.selectLovByLovType(reqJson.getLovType());
			log.info("return success from LovHomeDao.selectLovByLovType() with list:" + list);
			if (null == list || list.size() == 0) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_NO_SECQUE_FOUND.getCode());
			}
			respJson.setLov(list);
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}
		log.info("return successfully with respJson:" + respJson);
		return respJson;
	}
}
