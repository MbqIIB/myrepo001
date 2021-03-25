package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespListPspService;
import com.npst.upiserver.dao.ReqRespVaePspKeysDao;
import com.npst.upiserver.npcischema.RespListPsp;

@Service
public class UpiRespListPspServiceImpl implements UpiRespListPspService {
	private static final Logger log = LoggerFactory.getLogger(UpiRespListPspServiceImpl.class);

	@Autowired
	private ReqRespVaePspKeysDao reqRespVaePspKeysDao;

	@Override
	public void acquirerProcess(final RespListPsp respListPsp) {
		log.debug("respListPsp {}", respListPsp);
		reqRespVaePspKeysDao.updateRespListPsp(respListPsp);
	}

}
