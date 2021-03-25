package com.npst.upiserver.issuer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.npst.upiserver.issuer.service.UpiReqPendingMsgService;
import com.npst.upiserver.npcischema.ReqPendingMsg;

@Service
public class UpiReqPendingMsgServiceImpl implements UpiReqPendingMsgService {
	private static final Logger log = LoggerFactory.getLogger(UpiReqPendingMsgServiceImpl.class);

	@Override
	public void issuerProcess(final ReqPendingMsg reqPendingMsg) {
		log.debug("reqPendingMsg {]", reqPendingMsg);
	}

}
