package com.npst.upiserver.dao.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.dao.AuditUpiXmlDao;
import com.npst.upiserver.entity.Auditupixml;
import com.npst.upiserver.repo.AuditUpiXmlRepo;
import com.npst.upiserver.util.ErrorLog;

@Component
public class AuditUpiXmlDaoImpl implements AuditUpiXmlDao {
	private static final Logger log = LoggerFactory.getLogger(AuditUpiXmlDaoImpl.class);
	@Autowired
	private AuditUpiXmlRepo auditUpiXmlRepo;

	@Override
	public void insert(String xmlMsg, String txnType, String reqOrResp) {
		try {
			Auditupixml auditupixml = new Auditupixml();
			auditupixml.setAckfull("");
			auditupixml.setDate(new Date());
			auditupixml.setReqOrResp(reqOrResp.toUpperCase());
			auditupixml.setTxnType(txnType.toUpperCase());
			auditupixml.setReqrespfull(xmlMsg);
			auditUpiXmlRepo.save(auditupixml);
		} catch (Exception e) {
			log.error("Errro {} ", e);
			ErrorLog.sendError(e, AuditUpiXmlDaoImpl.class);
		}
	}

}
