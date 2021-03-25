package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespPendingMsgService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.RespPendingMsg;
import com.npst.upiserver.npcischema.ResultType;

@Service
public class UpiRespPendingMsgImpl implements UpiRespPendingMsgService {
	private static final Logger log = LoggerFactory.getLogger(UpiRespPendingMsgImpl.class);

	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	MobReqRespJsonDao mobReqRespJsonDao;
	
	@Override
	public void acquirerProcess(final RespPendingMsg respPendingMsg , final String message) {
		log.debug("respPendingMsg {}", respPendingMsg);
		try {
			Long idmobreqrespjsonid = mobReqRespJsonIdDao.getMobReqRespJsonId(respPendingMsg.getResp().getReqMsgId());
			if(idmobreqrespjsonid>0) {
				ReqResp respJson = new ReqResp();
				respJson.setTxnType(respPendingMsg.getTxn().getType().toString());
				if (ResultType.SUCCESS.toString().equals(respPendingMsg.getResp().getResult())) {
					respJson.setMsg(ResultType.SUCCESS.toString());
					respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
				} else {
					respJson.setMsg(respPendingMsg.getResp().getErrCode());
					respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
				}
				mobReqRespJsonDao.finalDbUpdate(respJson, idmobreqrespjsonid);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
