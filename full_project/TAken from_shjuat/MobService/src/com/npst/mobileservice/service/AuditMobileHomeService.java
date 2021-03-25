package com.npst.mobileservice.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.AuditMobileServiceHome;
import com.npst.upi.hor.Auditmobileservice;

/**
 * @author Sumaiya Ahmad
 * @since 6/07/2017
 */

public class AuditMobileHomeService {
	private static AuditMobileServiceHome auditMobileServiceHome = null;
	private static final Logger log = Logger.getLogger(AuditMobileHomeService.class);

	public void auditLogs(String reqStr, String respStr, Date reqDate, Date respDate, String str, String authToken,String source,String node) {
		log.info("");
		Auditmobileservice auditmobileservice = new Auditmobileservice();
		auditmobileservice.setReqjson(reqStr);
		auditmobileservice.setRespjson(respStr);
		auditmobileservice.setReqjsondt(reqDate);
		auditmobileservice.setRespjsondt(respDate);
		auditmobileservice.setApiname(str);
		auditmobileservice.setAuthtoken(authToken);
		auditmobileservice.setSource(source);
		auditmobileservice.setNode(node);
		if (null == auditMobileServiceHome) {
			auditMobileServiceHome = new AuditMobileServiceHome();
		}
		auditMobileServiceHome.insert(auditmobileservice);
	}

}
