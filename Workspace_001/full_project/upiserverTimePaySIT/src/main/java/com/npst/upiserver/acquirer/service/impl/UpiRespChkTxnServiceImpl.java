package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespChkTxnService;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.npcischema.RespChkTxn;

@Service
public class UpiRespChkTxnServiceImpl implements UpiRespChkTxnService {
	private static final Logger log = LoggerFactory.getLogger(UpiRespChkTxnServiceImpl.class);

	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	MobUpiReqRespJsonDao mobUpiReqRespJson;
	
	@Autowired
	MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	
	@Override
	public void acquirerProcess(final RespChkTxn respChkTxn,final String message) {
		log.debug("respChkTxn {}", respChkTxn);
		try {
			Long idmobreqrespjsonid = mobUpiReqRespJsonIdDao.getMobReqRespJsonId(respChkTxn.getResp().getReqMsgId());
			if (0 != idmobreqrespjsonid) {
				mobUpiReqRespJson.updateDb(idmobreqrespjsonid, respChkTxn, message);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
