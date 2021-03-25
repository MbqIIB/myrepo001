package com.npst.upiserver.issuer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.dao.ReqRespHbtDao;
import com.npst.upiserver.issuer.service.UpiRespHbtService;
import com.npst.upiserver.npcischema.RespHbt;
import com.npst.upiserver.util.ErrorLog;

@Service
public class UpiRespHbtServiceImpl implements UpiRespHbtService {

	private static final Logger log = LoggerFactory.getLogger(UpiRespHbtServiceImpl.class);
	@Autowired
	private ReqRespHbtDao reqRespHbtDao;

	@Override
	public void issuerProcess(final RespHbt respHbt) {
		log.debug("respHbt {}", respHbt);
		try {
			reqRespHbtDao.updateResp(respHbt);
		} catch (Exception e) {
			log.info("error in respHbt {}", e);
ErrorLog.sendError(e, UpiRespHbtServiceImpl.class);
		}

	}
}
