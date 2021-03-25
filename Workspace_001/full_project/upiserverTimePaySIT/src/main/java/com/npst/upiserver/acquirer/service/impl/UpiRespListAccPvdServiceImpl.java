package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespListAccPvdService;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespListAccPvdDao;
import com.npst.upiserver.npcischema.RespListAccPvd;

@Service
public class UpiRespListAccPvdServiceImpl implements UpiRespListAccPvdService {
	private static final Logger log = LoggerFactory.getLogger(UpiRespListAccPvdServiceImpl.class);
	
	@Autowired
	private ReqRespListAccPvdDao reqRespListAccPvdDao;

	
	@Autowired
	private MobReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	private MobReqRespJsonDao mobReqRespJsonDao;
	
	
	@Override
	public void acquirerProcess(final RespListAccPvd respListAccPvd,final String message) {
		log.debug("respListAccPvd {}", respListAccPvd);
		try {
			Long idmobreqrespjsonid = mobReqRespJsonIdDao.getMobReqRespJsonId(respListAccPvd.getResp().getReqMsgId());
			if(idmobreqrespjsonid>0) {
//				mobReqRespJsonDao.updateDb(idmobreqrespjsonid, respListAccPvd, message);
				reqRespListAccPvdDao.updateResp(respListAccPvd);
			}
		} catch (Exception e) {
			log.error("error {}", e);
		}
	}
}
