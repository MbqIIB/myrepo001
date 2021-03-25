package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespBalEnqService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespBalEnqDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.RespBalEnq;
import com.npst.upiserver.npcischema.ResultType;

@Service
public class UpiRespBalEnqServiceImpl implements UpiRespBalEnqService {
	private static final Logger log = LoggerFactory.getLogger(UpiRespBalEnqServiceImpl.class);

	@Autowired
	MobReqRespJsonDao mobReqRespJsonDao;
	
	@Autowired
	ReqRespBalEnqDao reqRespBalEnq;
	
	@Autowired
	CustomerTxnsDao custTxnDao;
	
	@Autowired
	MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	
	
	@Override
	public void acquirerProcess(final RespBalEnq respBalEnq,final String message) {
		log.debug("respBalEnq {}", respBalEnq);
		try {
			Long idmobreqrespjsonid = mobUpiReqRespJsonIdDao.getMobReqRespJsonId(respBalEnq.getResp().getReqMsgId());
			if(idmobreqrespjsonid>0) {
				ReqResp respJson = new ReqResp();
				respJson.setTxnType(respBalEnq.getTxn().getType().toString());
				if (ResultType.SUCCESS.toString().equalsIgnoreCase(respBalEnq.getResp().getResult())) {
					respJson.setRespBal(respBalEnq.getPayer().getBal().getData().getValue());
					respJson.setMsg(respBalEnq.getPayer().getBal().getData().getValue());
					respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
				} else {
					respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
					respJson.setMsg(respBalEnq.getResp().getErrCode());
				}
				mobReqRespJsonDao.finalDbUpdate(respJson, idmobreqrespjsonid);
				
			}
			reqRespBalEnq.updateResp(respBalEnq);
			custTxnDao.update(respBalEnq);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}