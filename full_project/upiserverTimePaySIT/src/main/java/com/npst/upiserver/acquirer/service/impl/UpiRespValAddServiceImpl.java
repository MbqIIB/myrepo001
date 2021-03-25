package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespValAddService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespValAddDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.RespValAdd;
import com.npst.upiserver.npcischema.ResultType;

@Service
public class UpiRespValAddServiceImpl implements UpiRespValAddService {

	private static final Logger log = LoggerFactory.getLogger(UpiRespValAddServiceImpl.class);

	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	MobReqRespJsonDao mobReqRespJsonDao;
	
	@Autowired
	ReqRespValAddDao reqRespValAddDao;
	
	@Autowired
	MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	
	@Override
	public void acquirerProcess(final RespValAdd respValAdd,final String message) {
		log.debug("respValAdd {}", respValAdd);
		try {
			Long idmobreqrespjsonid = mobUpiReqRespJsonIdDao.getMobReqRespJsonId(respValAdd.getResp().getReqMsgId());
			if(idmobreqrespjsonid>0) {
				ReqResp respJson = new ReqResp();
				respJson.setTxnType(respValAdd.getTxn().getType().toString());
				if (ResultType.SUCCESS.toString().equals(respValAdd.getResp().getResult())) {
					respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
					respJson.setMsg(respValAdd.getResp().getMaskName());
				} else {
					respJson.setMsg(respValAdd.getResp().getErrCode());
					respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
				}
				mobReqRespJsonDao.finalDbUpdate(respJson, idmobreqrespjsonid);
			}
			reqRespValAddDao.updateResp(respValAdd);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
