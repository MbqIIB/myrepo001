package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespListVaeService;
import com.npst.upiserver.dao.ReqRespVaePspKeysDao;
import com.npst.upiserver.npcischema.RespListVae;

@Service
public class UpiRespListVaeServiceImpl implements UpiRespListVaeService {

	private static final Logger log = LoggerFactory.getLogger(UpiRespListVaeServiceImpl.class);
	@Autowired
	private ReqRespVaePspKeysDao reqRespVaePspKeysDao;
	@Override
	public void acquirerProcess(final RespListVae respListVae) {
		log.debug("respListVae {}", respListVae);
		reqRespVaePspKeysDao.updateListVae(respListVae);
		
	}
}
